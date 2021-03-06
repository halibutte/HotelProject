/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
    private double extraSpend;
    private static Map<String, String> classNameMap = initClassNameMap();

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

    public double getExtraSpend() {
        return extraSpend;
    }

    public void setExtraSpend(double extraSpend) {
        this.extraSpend = extraSpend;
    }
    
    public double totalIncome() {
        return extraSpend + income;
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
    
    private static Map<String, String> initClassNameMap() {
        Map<String, String> temp = new HashMap<>();
        temp.put("sup_d", "Superior Double");
        temp.put("sup_t", "Superior Twin");
        temp.put("std_d", "Standard Double");
        temp.put("std_t", "Standard Twin");
        return temp;
    }
    public String getRoomClassFull() {
        if(classNameMap.containsKey(roomClass)) {
            return classNameMap.get(roomClass);
        } else {
            return "Unknown Type";
        }
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
