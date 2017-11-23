/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel.Managers;

import DataModel.Customer;
import DataModel.Model;
import java.util.List;
import java.util.Map;

/**
 *
 * @author x3041557
 */
public class CustomerManager extends AbstractManager {
    public CustomerManager(Model model) {
        super(model);
    }
    
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
                CustomerManager::mapToCustomer 
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
                CustomerManager::mapToCustomer
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
}
