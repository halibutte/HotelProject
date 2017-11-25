/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.time.LocalDate;

/**
 *
 * @author Hal
 */
public class RoomBooking {
    private Integer roomNo;
    private Integer ref;
    private LocalDate checkin;
    private LocalDate checkout;

    public RoomBooking() {
    }

    public RoomBooking(Integer no, Integer ref, LocalDate checkin, LocalDate checkout) {
        this.roomNo = no;
        this.ref = ref;
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public Integer getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }
    
    public String toString() {
        return this.getRoomNo() + "::" + this.getCheckin() + "::" + this.getCheckout() + "::" + this.getRef();
    }
}
