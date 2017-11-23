/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;

/**
 *
 * @author x3041557
 */
public class Model {
    //Intended to handle create, read, update, delete operations for all the
    //entity types included in the model
    private ConnectionManager conn;
    private Map<String, PreparedStatement> statementMap = new HashMap<>();

    public Model() throws IllegalStateException {
        try {
            //initialise the connection manager
            conn = new ConnectionManager();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to initialise database connection");
        }
    }
      
    
    //<editor-fold defaultstate="collapsed" desc="Shared Database Methods">
    private PreparedStatement prepareStatement(String sql, String key) 
            throws SQLException 
    {
        PreparedStatement stmt;
        if(!statementMap.containsKey(key)) {
            stmt = conn.getConnection().prepareStatement(sql);
            statementMap.put(key, stmt);
        } else {
            stmt = statementMap.get(key);
        }
        return stmt;
    }
    
    private List<Object> createFromRecordset (
            Function<Map<String,Object>,Object> mapFunction,
            ResultSet rs
    ) throws SQLException {
        //this is a generic method to take a result set from a select query,
        //and map these to a specific object type using the sepcified Function
        //result is typed as object as we do not know what will get returned
        List<Object> list = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        while(rs.next()) {
            Map<String, Object> rowMap = new HashMap<>();
            //make a map of column name to value
            //iterate through metadata of column names
            for(int i = 1; i <= colCount ; i++ ) {
                String name = rsmd.getColumnName(i);
                Object val = rs.getObject(name);
                rowMap.put(name, val);
            }
            //pass this map of colname / value to the mapping function
            Object obj = mapFunction.apply(rowMap);
            //add the result to the list
            list.add(obj);
        }
        return list;
    }
    
    List<Object> getList(
            String sql, 
            Object[] args, 
            String stmtKey,
            Function<Map<String,Object>,Object> mapFunc
    ) 
    {
        List<Object> result = new ArrayList<>();
        try {
            PreparedStatement localStmt = prepareStatement(sql, stmtKey);
            addArgs(localStmt, args);
            ResultSet rs = localStmt.executeQuery();
            result = createFromRecordset(mapFunc, rs);
        } catch (Exception e) {
            return null;
        } finally {
            return result;
        }
    }
    
    Object getSingle(
            String sql, 
            Object[] args, 
            String stmtKey,
            Function<Map<String,Object>,Object> mapFunc
    ) 
    {
        List<Object> all = getList(sql, args, stmtKey, mapFunc);
        try {
            if(all.size() > 1 || all.size() <= 0 ) {
                return null;
            } else {
                return all.get(0);
            }
        } catch (NullPointerException e) {
            return null;
        }
    }
    
    int createRecord(String sql, Object[] args, String stmtKey ) {
        int result = -1;
        try {
            PreparedStatement localStmt = prepareStatement(sql, stmtKey);
            addArgs(localStmt, args);
            result = localStmt.executeUpdate();
        } catch (Exception e) {
            result = -1;
            //e.printStackTrace();
        } finally {
            return result;
        }
    }
    
    public void addArgs(PreparedStatement stmt, Object[] args) throws SQLException {
        for(int i = 1; i <= args.length; i++) {
            stmt.setObject(i, args[i-1]);
        }
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="CUSTOMER">
    public int createCustomer(Customer customer) {
        //return value is the id of the customer just created
        //return value is -1 if error
        //create this prepared statement if it has not already been use
        String sql = "INSERT INTO hotelbooking.customer("
                + "c_name, c_email, c_address, c_cardtype, c_cardexp, c_cardno)"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        //set up arguments
        Object[] args = new Object[] {
            customer.getName(),
            customer.getEmail(),
            customer.getEmail(),
            customer.getCardtype(),
            customer.getCardexp(),
            customer.getCardno()
        };

        return createRecord(sql, args, "createCustomer");
    }
    
    public Customer getCustomer(int cno) {
        //return a customer based on their id
        //if not in databse or error retrieving, will return null
        String sql = "SELECT * FROM customer WHERE c_no = ?";
        Object[] args = {cno};
        return (Customer)getSingle(
                sql, 
                args, 
                "getCustomerInt", 
                Model::mapToCustomer 
        );
    }
    
    public Customer getCustomer(String email) {
        //return a customer based on their email (should also be unique)
        return new Customer();
    }
    
    public List<Customer> getAllCustomers() {
        //return a list of all customers
        String sql = "SELECT * FROM customer";
        Object[] args = {};
        List<Object> results = getList(
                sql, 
                args, 
                "getAllCustomers", 
                Model::mapToCustomer
        );
        return (List<Customer>)(List<?>)results;
    }
    
    private static Customer mapToCustomer(Map<String, Object> map) {
        //This method takes a map of key: column name, val: column value
        //and create a new Customer object based on this.
        //This should be passed to the createFromRecordset method
        Customer customer = new Customer();
        customer.setAddress((String)map.get("c_address"));
        customer.setCardexp((String)map.get("c_name"));
        customer.setCardno((String)map.get("c_cardno"));
        customer.setCardtype((String)map.get("c_cardtype"));
        customer.setEmail((String)map.get("c_email"));
        customer.setName((String)map.get("c_name"));
        customer.setNo((int)map.get("c_no"));
        return customer;
    }
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ROOM">
    public Room getRoom(int roomNo) {
        String sql = "SELECT * FROM room WHERE r_no = ?";
        Object[] args = {roomNo};
        Room result = (Room)getSingle(
                sql, 
                args, 
                "getRoomInt", 
                Model::mapToRoom
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
                Model::mapToRoom
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
                Model::mapToRoom
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
                Model::mapToRoom
        );
        return result;
    }
    
    public List<Room> getRoomsAvailByDate(LocalDate checkin, LocalDate checkout) {
        String sql = "SELECT * FROM rooms_avail(?, ?)";
        Object[] args = {
            Date.valueOf(checkin),
            Date.valueOf(checkout)
        };
        return (List<Room>)(List<?>)getList(sql, args, "getRoomsAvail", Model::mapToRoom);
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
    
    //<editor-fold defaultstate="collapsed" desc="ROOMBOOKING">
    private static RoomBooking mapToRoomBooking(Map<String, Object> map) {
        RoomBooking rb = new RoomBooking();
        rb.setCheckin(LocalDate.parse(map.get("checkin").toString()));
        rb.setCheckout(LocalDate.parse(map.get("checkout").toString()));
        rb.setRef((Integer)map.get("b_ref"));
        rb.setRoomNo((Integer)map.get("r_no"));
        return rb;
    }
    
    public List<RoomBooking> getAllRoomBookings() {
        String sql = "SELECT * FROM roombooking";
        Object[] args = {};
        return (List<RoomBooking>)(List<?>)getList(sql, args, "getRoomBookingAll", Model::mapToRoomBooking);
    }
    
    public List<RoomBooking> getRoomBookings(LocalDate start, LocalDate end) {
        //gets all room bookings which cover any of this period
        String sql = "SELECT * FROM roombooking WHERE (checkin <= ? AND checkout > ?) OR (checkin BETWEEN ? AND ?)";
        Object[] args = {
            Date.valueOf(start), 
            Date.valueOf(start), 
            Date.valueOf(start), 
            Date.valueOf(end)
        };
        return (List<RoomBooking>)(List<?>)getList(sql, args, "getRoomBookingsDateRange", Model::mapToRoomBooking);
    }
    
    public List<RoomBooking> getRoomBookings(int bookingRef) {
        String sql = "SELECT * FROM roombooking WHERE b_ref = ?";
        Object[] args = {bookingRef};
        return (List<RoomBooking>)(List<?>)getList(sql, args, "getRoomBookingsBookingRef", Model::mapToRoomBooking);
    }
    //</editor-fold>
      
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
        booking.setRooms(this.getRoomBookings(booking.getRef()));
        booking.setCustomer(this.getCustomer(booking.getCustomerNo()));
        return booking;
    }
    
    private Booking getBooking(int ref) {
        String sql = "SELECT * FROM booking WHERE b_ref = ?";
        Object[] args = {ref};
        return (Booking)getSingle(sql, args, "getBookingRef", m -> mapToBooking(m));
    }
    //</editor-fold>
    
    public static void main(String[] args) {
        Model m = new Model();
        Customer c = new Customer("Pam Satan II", "a@b.c", "Norwich", "V", "02/12", "2136871466");
        m.createCustomer(c);
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
            m.getRoomsAvailByDate(LocalDate.of(2017, 12, 24), LocalDate.of(2017, 12, 28)).stream().forEach(r -> System.out.println(r));
            Booking b = m.getBooking(13505);
            int foo = 1;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Room r = (Room)null;
    }
}
