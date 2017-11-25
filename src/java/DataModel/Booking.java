/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hal
 */
public class Booking {
    //returns basic booking info, also has related information in lists
    //UPDATES TO CHILD ITEMS ARE NOT CURRENTLY SAVED WHEN UPDATING THIS BOOKING
    //UPDATES TO CHILD ITEMS MUST BE MADE INDIVIDUALLY
    private Integer ref;
    private Integer customerNo;
    private double cost;
    private double outstanding;
    private String notes;
    private List<RoomBooking> rooms;
    private Customer customer;

    public Booking() {
    }

    public Booking(Integer ref, Integer customerNo, double cost, double outstanding, String notes) {
        this.ref = ref;
        this.customerNo = customerNo;
        this.cost = cost;
        this.outstanding = outstanding;
        this.notes = notes;
    }

    public double getCost() {
        return cost;
    }

    public Integer getCustomerNo() {
        return customerNo;
    }

    public double getOutstanding() {
        return outstanding;
    }
    
    public String getOutstandingString() {
        DecimalFormat format = new DecimalFormat("###,##0.00");
        return format.format(outstanding);
    }

    public String getNotes() {
        return notes;
    }

    public Integer getRef() {
        return ref;
    }

    public List<RoomBooking> getRooms() {
        return rooms;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setCustomerNo(Integer customerNo) {
        this.customerNo = customerNo;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setOutstanding(double outstanding) {
        this.outstanding = outstanding;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public void setRooms(List<RoomBooking> rooms) {
        this.rooms = rooms;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String toString() {
        return "ID: " + this.getRef() + "||" +customer + "||Rooms " + rooms.size();
    }
}
