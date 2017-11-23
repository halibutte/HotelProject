/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel.Managers;

import DataModel.Model;
import DataModel.Room;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
        String sql = "SELECT * FROM room WHERE r_no = ?";
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
    
    public Map<String,Long> getCountRoomsAvailByDate(LocalDate checkin, LocalDate checkout) {
        //returns a number of available rooms for a specific date range
        List<Room> avail = getRoomsAvailByDate(checkin, checkout);
        Map<String,Long> counts = avail.stream()
                .map(r -> r.getRoomClass())
                .collect(Collectors.groupingBy(
                        Function.identity(), 
                        Collectors.counting()));
        return counts;
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
}
