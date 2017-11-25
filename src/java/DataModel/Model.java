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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public Model() throws ModelException {
        try {
            //initialise the connection manager
            conn = new ConnectionManager();
            ROOMS = new RoomManager(this);
            CUSTOMERS = new CustomerManager(this);
            ROOMBOOKINGS = new RoomBookingManager(this);
            BOOKINGS = new BookingManager(this);
        } catch (SQLException e) {
            throw new ModelException(e.getMessage());
        }
    }
    
    public ConnectionManager getConnection() {
        return conn;
    }
  
    public static void main(String[] args) {
        //Room request = new Room();
        //request.setRoomClass("sup_d");
        try {
            Model m = new Model();
            //Customer c = new Customer("Pam Satan III", "bad@inter.net", "Norwich", "V", "02/12", "2136871466");
            //c = m.CUSTOMERS.getCustomer(c.getName(), c.getEmail());
            /*Customer pam = m.CUSTOMERS.createCustomer(c);
            System.out.println(pam);
            List<Room> list = new ArrayList<>();
            list.add(request);
            //have pam make bookings for this date until she has used up all the available rooms, should exception
            //out
            for(int i = 0; i < 100; i++) {
                m.BOOKINGS.makeBooking(c, list, LocalDate.parse("2017-12-24"), LocalDate.parse("2017-12-28"));
            }
            //Booking booktest = m.BOOKINGS.getBooking(15976);
            int foo = 1;*/
            //c.setName("Jordan Jorgens");
            //m.CUSTOMERS.updateCustomer(c);
            //Customer check = m.CUSTOMERS.getCustomer(12955);
            //System.out.println(check);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Room r = (Room)null;
    }
}
