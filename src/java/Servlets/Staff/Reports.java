/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Staff;

import DataModel.Model;
import DataModel.ModelException;
import DataModel.Report;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author x3041557
 */
public class Reports extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //get parameters from form
            String startDate = (String)request.getParameter("start_date");
            String endDate = (String)request.getParameter("end_date");
            LocalDate start = LocalDate.now();
            LocalDate end = LocalDate.now();
            //List for messages
            List<String> messages = new ArrayList<>();
            
            if(!Objects.isNull(startDate)) {
                //have received a start and end date from form
                //try to parse them into a Date type
                try {
                    start = LocalDate.parse(startDate);
                    end = LocalDate.parse(endDate);
                } catch (Exception e) {
                    //leave as defaults
                    messages.add("error#Could not parse dates, used todays date as default");
                }
            }
            List<Report> report = null;
            try {
                Model m = new Model();
                report = m.REPORTS.weeklyReport(start, end);
            } catch(ModelException e) {
                //handle
                messages.add("error#Could not retrieve reports from database");
            }
            
            //forward response
            request.setAttribute("report_list", report);
            request.setAttribute("messages", messages);
            request.setAttribute("start_date", start);
            request.setAttribute("end_date", end);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Staff/reports.jsp");
            dispatcher.forward(request, response);
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
