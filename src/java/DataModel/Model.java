/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import DataModel.Managers.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author x3041557
 */
public class Model {
    //Intended to handle create, read, update, delete operations for all the
    //entity types included in the model
    private ConnectionManager conn;
    private Map<String, PreparedStatement> statementMap = new HashMap<>();
    public RoomManager ROOMS;
    public CustomerManager CUSTOMERS;
    public RoomBookingManager ROOMBOOKINGS;
    public BookingManager BOOKINGS;

    public Model() throws IllegalStateException {
        try {
            //initialise the connection manager
            conn = new ConnectionManager();
            ROOMS = new RoomManager(this);
            CUSTOMERS = new CustomerManager(this);
            ROOMBOOKINGS = new RoomBookingManager(this);
            BOOKINGS = new BookingManager(this);
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to initialise database connection");
        }
    }
    
    public ConnectionManager getConnection() {
        return conn;
    }
  
    public static void main(String[] args) {
        Model m = new Model();
        Customer c = new Customer("Pam Satan II", "a@b.c", "Norwich", "V", "02/12", "2136871466");
        m.CUSTOMERS.createCustomer(c);
        try {
//            System.out.println(m.getCustomer(12008));
//            System.out.println(m.getCustomer(666));
//            m.getAllCustomers().stream().forEach(cust -> System.out.println(cust));
//            System.out.println(m.getRoom(101));
//            m.getRoomByClass("sup_d").stream().forEach(room -> System.out.println(room));
//            System.out.println("");
//            m.getRoomByStatus("A").stream().forEach(room -> System.out.println(room));
//            System.out.println("==========ALL ROOM=============");
//            m.getAllRooms().stream().forEach(room -> System.out.println(room));
//            System.out.println(m.getAllRooms().size());
//            m.getAllRoomBookings().stream().forEach(room -> System.out.println(room));
//            System.out.println("==========DATE RANGE=============");
//            m.getRoomBookings(LocalDate.of(2017, 12, 24), LocalDate.of(2017, 12, 28)).stream().forEach(rb -> System.out.println(rb));
            m.ROOMS.getRoomsAvailByDate(LocalDate.of(2017, 12, 24), LocalDate.of(2017, 12, 28)).stream().forEach(r -> System.out.println(r));
            Booking b = m.BOOKINGS.getBooking(13505);
            System.out.println(b);
            int foo = 1;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Room r = (Room)null;
    }
}
