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
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
            throw new IllegalStateException("Not all rooms available");
        }
        
        //required rooms are available, so go ahead and complete booking
        //currently assumes customer is new TO CHANGE
        Customer c = model.CUSTOMERS.createCustomer(customer);
        //create a booking object
        Booking b = createBooking(c.getNo(), 0, "Test Booking");
        //create relevant roombookings
        List<Room> availRooms = model.ROOMS.getRoomsAvailByDate(checkin, checkout);
        for(Room r : rooms) {
            //select first available room
            Optional<Room> selectedRoom = availRooms.stream()
                    .filter(t -> t.getRoomClass().equals(r.getRoomClass()))
                    .findFirst();
            
            //create a booking for this room
            RoomBooking make = new RoomBooking(
                    selectedRoom.get().getNo(), 
                    b.getRef(), 
                    checkin, 
                    checkout
            );
            RoomBooking completed = model.ROOMBOOKINGS.createRoomBooking(make);
            b.getRooms().add(completed);
        }
        return getBooking(b.getRef());
    }
    
    private boolean allAvail(List<Room> rooms, LocalDate checkin, LocalDate checkout) {
        //check all the requires rooms in the list are available
        //rooms only need to specify the type
        List<Room> avail = model.ROOMS.getRoomsAvailByDate(checkin, checkout);
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
}
