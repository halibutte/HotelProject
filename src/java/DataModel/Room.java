/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Hal
 */
public class Room implements Comparable {
    private Integer no;
    private String roomClass;
    private String status;
    private String notes;
    private double price;
    private Map<String, String> statusLookup = Room.createStatusMap();

    public Room() {
    }

    public Room(Integer no, String room_class, String status, String notes) {
        this.no = no;
        this.roomClass = room_class;
        this.status = status;
        this.notes = notes;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getLongStatus() {
        Optional<String> desc = statusLookup.entrySet().stream()
                .filter((e) -> e.getKey().equals(this.getStatus()))
                .map((e) -> e.getValue())
                .findFirst();
        String res = "Invalid State";
        if(desc.isPresent()) {
            res = desc.get();
        }
        return res;
    }

    public String getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(String roomClass) {
        this.roomClass = roomClass;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public String toString() {
        return this.getNo() + "::" + this.getRoomClass() + "::" + this.getStatus() + "::" + this.getNotes() + "::" + this.getPrice();
    }

    static Map<String, String> createStatusMap() {
        Map<String, String> map = new HashMap<>();
        map.put("C", "Checked Out");
        map.put("X", "Unavailable");
        map.put("O", "Occupied");
        map.put("A", "Available");
        return map;
    }
    
    @Override
    public int compareTo(Object o) {
        return Integer.compare(this.getNo(), ((Room)o).getNo());
    }
    
    
}
