/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.time.LocalDate;

/**
 *
 * @author x3041557
 */
public class Report implements Comparable {
    //class to hold results of a weekly report
    private LocalDate startDate;
    private LocalDate endDate;
    private int nightsOccupied;
    private double income;
    private double percentOccupancy;
    private String roomClass;
    private int nightsAvail;

    public Report() {
    }

    public Report(LocalDate startDate, LocalDate endDate, int nightsOccupied, double income, double percentOccupancy, String roomClass, int nightsAvail) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.nightsOccupied = nightsOccupied;
        this.income = income;
        this.percentOccupancy = percentOccupancy;
        this.roomClass = roomClass;
        this.nightsAvail = nightsAvail;
    }

    public int getNightsAvail() {
        return nightsAvail;
    }

    public void setNightsAvail(int nightsAvail) {
        this.nightsAvail = nightsAvail;
    }

    
    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public double getIncome() {
        return income;
    }

    public int getNightsOccupied() {
        return nightsOccupied;
    }

    public double getPercentOccupancy() {
        return percentOccupancy;
    }

    public String getRoomClass() {
        return roomClass;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void setNightsOccupied(int nightsOccupied) {
        this.nightsOccupied = nightsOccupied;
    }

    public void setPercentOccupancy(double percentOccupancy) {
        this.percentOccupancy = percentOccupancy;
    }

    public void setRoomClass(String roomClass) {
        this.roomClass = roomClass;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public int compareTo(Object o) {
        Report in = (Report)o;
        if (in.getStartDate().isAfter(this.getStartDate())) {
            return -1;
        } else if(in.getStartDate().equals(this.getStartDate())) {
            return 0;
        } else {
            return 1;
        }
    }
    
     
}
