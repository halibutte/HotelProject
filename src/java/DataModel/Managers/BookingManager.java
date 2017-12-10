/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel.Managers;

import DataModel.Booking;
import DataModel.Customer;
import DataModel.Model;
import DataModel.ModelException;
import DataModel.Room;
import DataModel.RoomBooking;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author x3041557
 */
public class BookingManager extends AbstractManager {
    public BookingManager(Model model) {
        super(model);
    }
    
    //<editor-fold defaultstate="collapsed" desc="BOOKING">
    //booking mapping is a bit more detailed - want to get objects for some
    //related items. This cannot be static, must be passed using lambda syntax
    //eg. (map) -> mapToBooking(map)
    private Booking mapToBooking(Map<String,Object> map) {
        Booking booking = new Booking();
        booking.setCost(((BigDecimal)map.get("b_cost")).doubleValue());
        booking.setOutstanding(((BigDecimal)map.get("b_outstanding")).doubleValue());
        booking.setCustomerNo((Integer)map.get("c_no"));
        booking.setNotes((String)map.get("b_no"));
        booking.setRef((Integer)map.get("b_ref"));
        booking.setRooms(model.ROOMBOOKINGS.getRoomBookings(booking.getRef()));
        booking.setCustomer(model.CUSTOMERS.getCustomer(booking.getCustomerNo()));
        booking.setBilledItems(model.BILLABLES.getBilledItems(booking.getRef()));
        return booking;
    }
    
    public Booking getBooking(int ref) {
        String sql = "SELECT * FROM booking WHERE b_ref = ?";
        Object[] args = {ref};
        return (Booking)getSingle(sql, args, "getBookingRef", m -> mapToBooking(m));
    }
    
    public Booking makeBooking (
            Customer customer, 
            List<Room> rooms,
            LocalDate checkin,
            LocalDate checkout) throws ModelException 
    {
        //pass in a list of the require types of rooms desired, 
        //only need to provide type
        if(!allAvail(rooms, checkin, checkout)) {
            throw new ModelException("Not all rooms available");
        }
        
        //required rooms are available, so go ahead and complete booking
        //create customer will return existing record if there is one
        Customer c = model.CUSTOMERS.createCustomer(customer);
        //create a booking object
        Booking b = createBooking(c.getNo(), 0, "");
        //create relevant roombookings
        boolean done = bookList(rooms, checkin, checkout, b);
        return getBooking(b.getRef());
    }
    
    public Booking addRoom(int bookRef, List<Room> rooms) throws ModelException {
        //ensure this is a genuine booking
        Booking booking = getBooking(bookRef);
        if(Objects.isNull(booking)) {
            throw new ModelException("Booking does not exist");
        }
        
        //get the dates for this booking
        RoomBooking r = booking.getRooms().get(0);
        LocalDate checkin = r.getCheckin();
        LocalDate checkout = r.getCheckout();
        //check room is available
        if(!allAvail(rooms, checkin, checkout)) {
            throw new ModelException("Not all rooms are available");
        }
        
        //book the rooms
        boolean done = bookList(rooms, checkin, checkout, booking);
        return getBooking(booking.getRef());
    }
    
    private boolean bookList(
            List<Room> rooms, 
            LocalDate checkin, 
            LocalDate checkout,
            Booking booking
    ) throws ModelException
    {
        List<Room> availRooms = model.ROOMS.getRoomsAvailByDate(checkin, checkout);
        for(Room r : rooms) {
            //select first available room
            Optional<Room> selectedRoom = availRooms.stream()
                    .filter(t -> t.getRoomClass().equals(r.getRoomClass()))
                    .findFirst();
            
            //create a booking for this room
            RoomBooking make = new RoomBooking(
                    selectedRoom.get().getNo(), 
                    booking.getRef(), 
                    checkin, 
                    checkout
            );
            RoomBooking completed = model.ROOMBOOKINGS.createRoomBooking(make);
            boolean done = booking.getRooms().add(completed);
            if(!done) {
                throw new ModelException("Unable to add room, may be unavailable");
            }
            //remove the booked room from the list of available rooms
            availRooms.remove(selectedRoom.get());
        }
        return true;
    }
    
    private boolean allAvail(List<Room> rooms, LocalDate checkin, LocalDate checkout) {
        return allAvail(rooms, checkin, checkout, null);
    }
    
    private boolean allAvail(List<Room> rooms, LocalDate checkin, LocalDate checkout, Integer bref) {
        //check all the requires rooms in the list are available
        //rooms only need to specify the type
        List<Room> avail;
        if(Objects.isNull(bref)) {
            avail = model.ROOMS.getRoomsAvailByDate(checkin, checkout);
        } else {
            avail = model.ROOMS.getRoomsAvailByDate(checkin, checkout, bref);
        }
        final Map<String,Long> countAvail = avail.stream()
                .map(r -> r.getRoomClass())
                .collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()));
        Map<String,Long> countRequest = rooms.stream()
                .map(r -> r.getRoomClass())
                .collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()));
        
        //count number of impossible requests
        Integer notAvail = countRequest.entrySet().stream().mapToInt((Entry<String,Long> e) -> {
            //need to handle this not even being in the map as all booked
            if(!countAvail.containsKey(e.getKey())) {
                return 1;
            }
            if(countAvail.get(e.getKey()) < e.getValue()) {
                return 1;
            } else {
                return 0;
            }
        }).sum();
        
        return notAvail <= 0;
    }
    
    public Booking updateBooking(int bref, List<Room> rooms, LocalDate checkin, LocalDate checkout) throws ModelException {
        //Method to change a booking. Will first check if update is possible, will then delete all existing roombooking records
        //then create anew. All wraped up in a transaction, so if there is a fail midway we can roll back.
        Booking updated = null;
        try {
            //check the list of requested rooms is not empty
            if(rooms.size() <= 0) {
                throw new ModelException("List of requested rooms is empty");
            }
            
            model.getConnection().startTransaction();
            if(!allAvail(rooms, checkin, checkout, bref)) {
                throw new ModelException("Rooms not available");
            }
            
            //restore the booking
            Booking book = model.BOOKINGS.getBooking(bref);
            
            //delete all the roombookings from the list
            boolean done = model.ROOMBOOKINGS.deleteRoomsForBooking(bref);
            if(!done) {
                throw new ModelException("Failed to delete old roombookings");
            }
            
            //make new roombookings of the requested list
            if(!bookList(rooms, checkin, checkout, book)) {
                throw new ModelException("Failed to book all rooms");
            }
                       
            //retrieve the booking with updated info
            updated = model.BOOKINGS.getBooking(book.getRef());
            if(Objects.isNull(updated)) {
                throw new ModelException("Failed to retrieve booking");
            }
            
            //commmit all of this
            model.getConnection().commitTransaction();
        } catch (ModelException e) {
            model.getConnection().rollbackTransaction();
            throw new ModelException(e.getMessage());
        } catch (Exception e) {
            model.getConnection().rollbackTransaction();
        } 
        finally {
            model.getConnection().endTransaction();
        }
        return updated;
    }
    
    public Booking takePayment(Booking booking, double amount) throws ModelException {
        if(Objects.isNull(booking.getRef())) {
            throw new ModelException("Booking must have reference");
        }
        
        Customer c = booking.getCustomer();
        //do some validation
        if(!c.getCardno().matches("\\d{13,19}")) {
            throw new ModelException("Card number must be a 13 to 19 digit number");
        }

        //validate card exp
        if(c.getCardexp().matches("\\d{2}/\\d{2}")) {
            //convert to date
            String cardexp = c.getCardexp();
            String[] parts = cardexp.split("/");
            try {
                LocalDate expDate = LocalDate.of(Integer.parseInt("20"+parts[1]), Integer.parseInt(parts[0]), 1);
                LocalDate thisMonth = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
                if(expDate.isBefore(thisMonth)) {
                    throw new ModelException("Card has expired, please enter alternate payment details");
                }
            } catch (DateTimeException e) {
                throw new ModelException("Card expiry invalid, check month and year are correction");
            }
        } else {
            throw new ModelException("Enter card expiry in format MM/YY");
        }

        //validate card type
        String cardtype = c.getCardtype();
        if(!(cardtype.equals("V")||cardtype.equals("MC")||cardtype.equals("A"))) {
            throw new ModelException("Card type invalid");
        }
        
        String sql = "UPDATE booking SET b_outstanding = b_outstanding - ? WHERE b_ref = ?";
        Object[] args = {amount, booking.getRef()};
        if(updateRecord(sql, args, "bookingPayment")) {
            return getBooking(booking.getRef());
        } else {
            throw new ModelException("Unable to update booking");
        }
    }
    
    public Booking getCurrentOccupant(int rno) {
        //gets the booking which is currently checked into a room
        String sql = "SELECT booking.* " +
            "FROM roombooking JOIN room ON roombooking.r_no = room.r_no " +
            "JOIN booking ON roombooking.b_ref = booking.b_ref " +
            "WHERE room.r_status = 'O' AND roombooking.checkin <= current_date " +
            "AND roombooking.checkout >= current_date AND roombooking.r_no = ?";
        Object[] args = {rno};
        return (Booking)getSingle(sql, args, "getCurrentBookingForRoom", r -> mapToBooking(r));
    }
    
    public List<Booking> getAllOccupants(LocalDate date) {
        String sql = "SELECT booking.* FROM booking WHERE booking.b_ref IN " +
            "(SELECT roombooking.b_ref FROM roombooking " +
            "WHERE roombooking.checkin <= ? " +
            "AND roombooking.checkout >= ?)";
        Object[] args = {
            Date.valueOf(date),
            Date.valueOf(date)
        };
        return (List<Booking>)(List<?>)getList(sql, args, "getAllOccupantsOnDate", r -> mapToBooking(r));
    }
    
    public Booking getCheckinBooking(int rno, LocalDate checkin) {
        String sql = "SELECT booking.* "+
            "FROM roombooking NATURAL JOIN booking " +
            "WHERE roombooking.checkin = ? AND roombooking.r_no = ?";
        Object[] args = {Date.valueOf(checkin), rno};
        return (Booking)getSingle(sql, args, "getCheckinBookingForDate", r -> mapToBooking(r));
    }
    
    public Booking getCheckoutBooking(int rno, LocalDate checkout) {
        String sql = "SELECT booking.* "+
            "FROM roombooking NATURAL JOIN booking " +
            "WHERE roombooking.checkout = ? AND roombooking.r_no = ?";
        Object[] args = {Date.valueOf(checkout), rno};
        return (Booking)getSingle(sql, args, "getCheckoutBookingForDate", r -> mapToBooking(r));
    }
    
    private Booking createBooking(int custNo, double cost, String notes) throws ModelException {
        String sql = "INSERT INTO hotelbooking.booking(" +
            "c_no, b_cost, b_outstanding, b_notes)" +
            "VALUES (?, ?, ?, ?)";
        Object args[] = {custNo, cost, cost, notes};
        Object[] res = createRecord(sql, args, "createBooking");
        Integer res1 = (Integer)res[0];
        if(res1 > 0) {
            return getBooking(res1);
        } else if(res1 < 0) {
            throw new ModelException("SQL Exception while creating booking");
        } else {
            throw new ModelException("Failed to create, may violate unique constraints");
        }
    }
    //</editor-fold>
    
    public static void main(String[] args) throws ModelException {
        Model model = new Model();
        /*Customer cust = new Customer("Testing Name", "A@B.C", "Neston", "V", "20/17", "2304930498");
        Room room = new Room();
        room.setRoomClass("sup_d");
        List<Room> list = new ArrayList<>();
        list.add(room);
        Room extra = new Room();
        extra.setRoomClass("sup_t");
        List<Room> addOn = new ArrayList<>();
        addOn.add(extra);
        try {
            //make the booking
            //Booking b = model.BOOKINGS.makeBooking(cust, list, LocalDate.parse("2017-12-17"), LocalDate.parse("2017-12-18"));
            //System.out.println(b);
            //add a room on
            Booking b = model.BOOKINGS.getBooking(13906);
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //add something*/
        List<Room> askfor = new ArrayList<>();
        Room a = new Room();
        a.setRoomClass("std_t");
        askfor.add(a);
        try {
            model.BOOKINGS.updateBooking(13505, askfor, LocalDate.now(), LocalDate.parse("2017-12-09"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
