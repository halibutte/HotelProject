/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @author x3041557
 */
public class Model {
    //Intended to handle create, read, update, delete operations for all the
    //entity types included in the model
    private ConnectionManager conn;
    private PreparedStatement customerInsert;
    private PreparedStatement customerSelectCno;

    public Model() throws IllegalStateException {
        try {
            //initialise the connection manager
            conn = new ConnectionManager();
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to initialise database connection");
        }
    }
    
    private PreparedStatement prepareStatement(
            String sql, PreparedStatement stmt) 
            throws SQLException 
    {
        if(Objects.isNull(stmt)) {
            stmt = conn.getConnection().prepareStatement(sql);
        }
        return stmt;
    }
    
    private List<Object> createFromRecordset (
            Function<Map<String,Object>,Object> mapFunction, 
            ResultSet rs ) 
            throws SQLException
    {
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
    
    // <editor-fold defaultstate="collapsed" desc="CUSTOMER ">
    public int createCustomer(Customer customer) {
        //return value is the id of the customer just created
        //return value is -1 if error
        //create this prepared statement if it has not already been use
        String sql = "INSERT INTO hotelbooking.customer("
                + "c_no, c_name, c_email, c_address, c_cardtype, c_cardexp, c_cardno)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        //default to returning an error
        int retVal = -1;

        //wrapped in try to catch any SQL errors which occur
        try {
            //get the statement required to create a customer
            PreparedStatement stmt = prepareStatement(sql, customerInsert);
            //add our values from customer
            stmt.setObject(1, 666);
            stmt.setObject(2, customer.getName());
            stmt.setObject(3, customer.getEmail());
            stmt.setObject(4, customer.getEmail());
            stmt.setObject(5, customer.getCardtype());
            stmt.setObject(6, customer.getCardexp());
            stmt.setObject(7, customer.getCardno());
            int result = stmt.executeUpdate();
        } catch (SQLException e) {
            
        }
        
        return retVal;
    }
    
    public Customer getCustomer(int cno) {
        //return a customer based on their id
        //if not in databse or error retrieving, will return null
        String sql = "SELECT * FROM customer WHERE c_no = ?";
        List<Object> results = new ArrayList<>();
        try {
            PreparedStatement stmt = prepareStatement(sql, customerSelectCno);
            stmt.setInt(1, cno);
            ResultSet rs = stmt.executeQuery();
            results = createFromRecordset((r) -> mapToCustomer(r), rs);
        } catch (Exception e) {
            return null;
        } finally {
            if(results.size() > 1 || results.size() <= 0 ) {
                return null;
            } else {
                return (Customer)results.get(0);
            }
        }
    }
    
    public Customer getCustomer(String email) {
        //return a customer based on their email (should also be unique)
        return new Customer();
    }
    
    private Customer mapToCustomer(Map<String, Object> map) {
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
    
    
    public static void main(String[] args) {
        Model m = new Model();
        Customer c = new Customer(666, "Pam Satan", "a@b.c", "Norwich", "V", "02/12", "2136871466");
       // m.createCustomer(c);
        try {
            m.getCustomer(12008);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
