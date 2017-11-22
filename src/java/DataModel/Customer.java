/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.util.Objects;

/**
 *
 * @author x3041557
 */
public class Customer {
    //POJO to contain details of a Customer
    
// <editor-fold defaultstate="collapsed" desc="Member Variables">
    //customer number - should not be updated once created. does not need to be
    //specified when creathing
    Integer no = null;    
    String name;
    String email;
    String address; //billing address
    String cardtype;
    String cardexp;
    String cardno;

// </editor-fold>
    
    
// <editor-fold defaultstate="collapsed" desc=" Constructors ">
    public Customer() {
    }
    
    public Customer(int no, String name, String email, String address, String cardtype, String cardexp, String cardno) {
        this.no = no;
        this.name = name;
        this.email = email;
        this.address = address;
        this.cardtype = cardtype;
        this.cardexp = cardexp;
        this.cardno = cardno;
    }
// </editor-fold> 
    

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardexp() {
        return cardexp;
    }

    public void setCardexp(String cardexp) {
        this.cardexp = cardexp;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        if(Objects.isNull(this.no)) {
            this.no = no;
        }
    }
    
}