/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel.Managers;

import DataModel.Booking;
import DataModel.Model;
import DataModel.ModelException;
import DataModel.Room;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author x3041557
 */
public class RoomManager extends AbstractManager {
    public RoomManager(Model model) {
        super(model);
    }
    
    //<editor-fold defaultstate="collapsed" desc="ROOM">
    public Room getRoom(int roomNo) {
        String sql = "SELECT * FROM roomrate WHERE r_no = ?";
        Object[] args = {roomNo};
        Room result = (Room)getSingle(
                sql, 
                args, 
                "getRoomInt", 
                RoomManager::mapToRoom
        );
        return result;
    }
    
    public List<Room> getRoomsByClass(String roomClass) {
        String sql = "SELECT * FROM roomrate WHERE r_class = ?";
        Object[] args = {roomClass};
        List<Room> result = (List<Room>)(List<?>)getList(
                sql, 
                args, 
                "getRoomStatus", 
                RoomManager::mapToRoom
        );
        return result;
    }
    
    public List<Room> getRoomByStatus(String status) {
        String sql = "SELECT * FROM roomrate WHERE r_status = ?";
        Object[] args = {status};
        List<Room> result = (List<Room>)(List<?>)getList(
                sql, 
                args, 
                "getRoomClass", 
                RoomManager::mapToRoom
        );
        return result;
    }
     
    public List<Room> getAllRooms()  {
        String sql = "SELECT * FROM roomrate";
        Object[] args = {};
        List<Room> result = (List<Room>)(List<?>)getList(
                sql,
                args,
                "getRoomsAll",
                RoomManager::mapToRoom
        );
        return result;
    }
    
    public List<Room> getRoomsAvailByDate(LocalDate checkin, LocalDate checkout) {
        String sql = "SELECT * FROM rooms_avail(?, ?)";
        Object[] args = {
            Date.valueOf(checkin),
            Date.valueOf(checkout)
        };
        return (List<Room>)(List<?>)getList(sql, args, "getRoomsAvail", RoomManager::mapToRoom);
    }
    
    public List<Room> getRoomsAvailByDate(LocalDate checkin, LocalDate checkout, int bref) {
        //Finds rooms which are available between checkin and checkout, treating rooms which ae in booking bref as available
        //For use when trying to update a booking.
        String sql = "SELECT * FROM rooms_avail(?, ?, ?)";
        Object[] args = {
            Date.valueOf(checkin),
            Date.valueOf(checkout),
            bref
        };
        return (List<Room>)(List<?>)getList(sql, args, "getRoomsAvailEdit", RoomManager::mapToRoom);
    }
    
    public Map<String,Long> getCountRoomsAvailByDate(LocalDate checkin, LocalDate checkout) {
        return getCountRoomsAvailByDate(checkin, checkout, null);
    }
    public Map<String,Long> getCountRoomsAvailByDate(LocalDate checkin, LocalDate checkout, Integer bref) {
        //returns a number of available rooms for a specific date range
        List<Room> avail;
        if(Objects.isNull(bref)) {
            avail = getRoomsAvailByDate(checkin, checkout);
        } else {
            avail = getRoomsAvailByDate(checkin, checkout, bref);
        }
        Map<String,Long> counts = avail.stream()
                .map(r -> r.getRoomClass())
                .collect(Collectors.groupingBy(
                        Function.identity(), 
                        Collectors.counting()));
        return counts;
    }
    
    public Map<Room, Booking> getCurrentOccupancy() {
        //map the room to the booking it currently relates to
        List<Room> rooms = getAllRooms();
        Map<Room, Booking> map = new HashMap<>();
        for(Room r : rooms) {
            map.put(r, model.BOOKINGS.getCurrentOccupant(r.getNo()));
        }
        return map;
    }
    
    public Map<Room, Booking> getExpectedArrivals(LocalDate date) {
        String sql = "SELECT roomrate.r_no AS r_no, roomrate.r_class AS r_class, " +
            "roomrate.r_status AS r_status, roomrate.r_notes AS r_note, " +
            "roomrate.price AS price  " +
            "FROM roombooking NATURAL JOIN roomrate " +
            "WHERE roombooking.checkin = ?";
        Object[] args = {Date.valueOf(date)};
        List<Room> rooms = (List<Room>)(List<?>)getList(
                sql, 
                args, 
                "getArrivalsRooms", 
                RoomManager::mapToRoom
        );
        
        Map<Room, Booking> map = new TreeMap<>();
        for(Room r : rooms) {
            //get bookings for rooms
            map.put(r, model.BOOKINGS.getCheckinBooking(r.getNo(), date));
        }
        return map;
    }
    
    public Map<Room, Booking> getExpectedDepartures(LocalDate date) {
        String sql = "SELECT roomrate.r_no AS r_no, roomrate.r_class AS r_class, " +
            "roomrate.r_status AS r_status, roomrate.r_notes AS r_note, " +
            "roomrate.price AS price  " +
            "FROM roombooking NATURAL JOIN roomrate " +
            "WHERE roombooking.checkout = ?";
        Object[] args = {Date.valueOf(date)};
        List<Room> rooms = (List<Room>)(List<?>)getList(
                sql, 
                args, 
                "getDeparturesRooms", 
                RoomManager::mapToRoom
        );
        
        Map<Room, Booking> map = new TreeMap<>();
        for(Room r : rooms) {
            //get bookings for rooms
            map.put(r, model.BOOKINGS.getCheckoutBooking(r.getNo(), date));
        }
        return map;
    }
    
    public Room updateRoom(Room room) throws ModelException {
        if(Objects.isNull(room.getNo())) {
            throw new ModelException("Room number is not set");
        }
        
        String sql = "UPDATE room " +
            "SET r_class=?, r_status=?, r_notes=? " +
            "WHERE r_no = ?";
        
        Object[] args = {
            room.getRoomClass(),
            room.getStatus(),
            room.getNotes(),
            room.getNo()
        };
        
        boolean success = updateRecord(sql, args, "updateRoom");
        if(!success) {
            throw new ModelException("Room update failed");
        } else {
            return getRoom(room.getNo());
        }
    }
    
    public Map<String,Double> getRates() {
        List<Room> rooms = getAllRooms();
        Map<String,Double> map = new HashMap<>();
        for(Room r : rooms) {
            if(!map.containsKey(r.getRoomClass())) {
                map.put(r.getRoomClass(), Double.valueOf(r.getPrice()));
            }
        }
        return map;
    }

    private static Room mapToRoom(Map<String, Object> map) {
        Room room = new Room();
        room.setNo((Integer)map.get("r_no"));
        room.setRoomClass((String)map.get("r_class"));
        room.setStatus((String)map.get("r_status"));
        room.setNotes((String)map.get("r_notes"));
        room.setPrice(((BigDecimal)map.get("price")).doubleValue());
        return room;
    }
    //</editor-fold>
    
    public static void main(String[] args) {
        try { 
        Model m = new Model();
        Map<Room, Booking> occupant = m.ROOMS.getCurrentOccupancy();
        occupant.keySet().stream()
                .sorted((r1, r2) -> Integer.compare(r1.getNo(), r2.getNo()))
                .forEach((r) -> {
                    System.out.println(r + " || " + occupant.get(r));
                });
        
        //Select expected checkins for today
        Map<Room, Booking> checkins = m.ROOMS.getExpectedArrivals(LocalDate.parse("2017-12-26"));
        Map<Room, Booking> checkouts = m.ROOMS.getExpectedDepartures(LocalDate.parse("2017-12-26"));
        checkins.entrySet().forEach((r) -> 
        {
            System.out.println(r.getKey() + " |IN| " + r.getValue());
        });
        
        checkouts.entrySet().forEach((r) -> 
        {
            System.out.println(r.getKey() + " |OUT| " + r.getValue());
        }); 
        
        Room r203 = m.ROOMS.getRoom(203);
        r203.setStatus("O");
        m.ROOMS.updateRoom(r203);
        }
        
        
        catch (Exception e) {
            int foor = 1;
        }
    }
}
