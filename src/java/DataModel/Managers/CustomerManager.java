/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel.Managers;

import DataModel.Customer;
import DataModel.Model;
import DataModel.ModelException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author x3041557
 */
public class CustomerManager extends AbstractManager {
    public CustomerManager(Model model) {
        super(model);
    }
    
    // <editor-fold defaultstate="collapsed" desc="CUSTOMER">
    public Customer createCustomer(Customer customer) throws ModelException {
        //check if this customer exists. if it does, return it
        Customer check = getCustomer(customer.getName(), customer.getEmail());
        if(Objects.isNull(check)) {
            String sql = "INSERT INTO hotelbooking.customer("
                    + "c_name, c_email, c_address, c_cardtype, c_cardexp, c_cardno)"
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            Object[] args = new Object[] {
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getCardtype(),
                customer.getCardexp(),
                customer.getCardno()
            };

            Object[] result = createRecord(sql, args, "createCustomer");
            Integer res1 = (Integer)result[0];
            if(res1 > 0) {
                return getCustomer(res1);
            } else if (res1 == 0) {
                throw new ModelException("Unable to create customer, may violate unique constraints.");
            } else {
                throw new ModelException("SQL Exception when creating customer");
            }
        } else {
            return check;
        }
    }
    
    public boolean updateCustomer(Customer customer) throws ModelException {
        //if customer number is not set, see if we can match on name / email
        if(Objects.isNull(customer.getNo())) {
            Customer find = getCustomer(customer.getName(), customer.getEmail());
            if(Objects.isNull(find)) {
                throw new ModelException("Customer does not exist; use createCustomer to create new record");
            } else {
                customer.setNo(find.getNo());
            }
        }
        
        String sql = "UPDATE hotelbooking.customer " +
            "SET c_name=?, c_email=?, c_address=?, c_cardtype=?, " + 
            "c_cardexp=?, c_cardno=? WHERE c_no = ?";
        Object[] args = {
            customer.getName(),
            customer.getEmail(),
            customer.getAddress(),
            customer.getCardtype(),
            customer.getCardexp(),
            customer.getCardno(),
            customer.getNo()
        };
        
        return updateRecord(sql, args, "updateCustomer");
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
    
    public Customer getCustomer(String name, String email) {
        //assume a customer name / email combo is unique
        String sql = "SELECT * FROM customer WHERE c_name = ? AND c_email = ?";
        Object[] args = {name, email};
        return (Customer)getSingle(
                sql, 
                args, 
                "getCustomerNameEmail", 
                CustomerManager::mapToCustomer 
        );
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
        customer.setCardexp((String)map.get("c_cardexp"));
        customer.setCardno((String)map.get("c_cardno"));
        customer.setCardtype((String)map.get("c_cardtype"));
        customer.setEmail((String)map.get("c_email"));
        customer.setName((String)map.get("c_name"));
        customer.setNo((int)map.get("c_no"));
        return customer;
    }
    // </editor-fold>
    
    public static void main(String[] args) {
    }
}
