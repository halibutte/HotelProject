/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

/**
 *
 * @author x3041557
 */
public class BilledItem {
    private int id;
    private String code;
    private int bref;
    private String description;
    private double price;
    private BillableItem item;

    public BilledItem() {
    }

    public BilledItem(int id, String code, int bref, String description, double price, BillableItem item) {
        this.id = id;
        this.code = code;
        this.bref = bref;
        this.description = description;
        this.price = price;
        this.item = item;
    }

    public void setItem(BillableItem item) {
        this.item = item;
    }

    public BillableItem getItem() {
        return item;
    }

    public int getBref() {
        return bref;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setBref(int bref) {
        this.bref = bref;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
