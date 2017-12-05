/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Staff;

import DataModel.BillableItem;
import DataModel.Booking;
import DataModel.Model;
import DataModel.ModelException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author x3041557
 */
public class Billing extends HttpServlet {

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
            List<String> messages = new ArrayList<>();
            List<Booking> bookings = new ArrayList<>();
            List<BillableItem> items = new ArrayList<>();
            //get parameters from forms
            String viewDate = request.getParameter("view_date");
            LocalDate date = LocalDate.now();
            
            try {
                Model model = new Model();
                try {
                    date = LocalDate.parse(viewDate);
                } catch (Exception e) {
                    //leave as now
                }
                //get all the current bookings
                bookings = model.BOOKINGS.getAllOccupants(date);
                items = model.BILLABLES.getAllBillableItems();
            } catch (ModelException e) {
                messages.add("error#Problem connecting to database");
            }
            
            request.setAttribute("messages", messages);
            request.setAttribute("bookings", bookings);
            request.setAttribute("items", items);
            request.setAttribute("view_date", date);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Staff/billing.jsp");
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
