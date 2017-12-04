/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataModel.Managers;

import DataModel.Model;
import DataModel.Report;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author x3041557
 */
public class ReportManager extends AbstractManager {
        
    public ReportManager(Model model) {
        super(model);
    }
        
    private static Report mapToReport(Map<String,Object> map) {
        Report report = new Report();
        report.setEndDate(LocalDate.parse(map.get("date_end").toString()));
        report.setStartDate(LocalDate.parse(map.get("date_start").toString()));
        report.setRoomClass(map.get("r_class").toString());
        report.setIncome(((BigDecimal)map.get("income")).doubleValue());
        report.setPercentOccupancy(((BigDecimal)map.get("percent_occupancy")).doubleValue());
        report.setNightsOccupied(Integer.valueOf(((Long)map.get("nights_occupied")).intValue()));
        report.setNightsAvail(Integer.valueOf(((Long)map.get("nights_avail")).intValue()));
        return report;
    }
    
    public List<Report> weeklyReport(LocalDate start, LocalDate end) {
        String sql = "SELECT * FROM weekly_reports(?, ?)";
        Object[] args = {
            Date.valueOf(start),
            Date.valueOf(end)
        };
        
        return (List<Report>)(List<?>)getList(sql, args, "reportGetList", ReportManager::mapToReport);
    }
    
    public static void main(String[] args) {
        try {
            Model m = new Model();
            ReportManager rm = new ReportManager(m);
            List<Report> list = rm.weeklyReport(LocalDate.now(), LocalDate.now());
            list.stream().forEach(r -> System.out.println(r.getIncome()));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
