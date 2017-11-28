/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DataModel.Booking;
import DataModel.Customer;
import DataModel.Model;
import DataModel.ModelException;
import java.io.IOException;
import java.io.PrintWriter;
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
public class EditBooking extends HttpServlet {

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
            //customer details
            String bookRef = request.getParameter("bref");
            String custName = request.getParameter("cust_name");
            String custEmail = request.getParameter("cust_email");
            String custAddress = request.getParameter("cust_address");
            //dates
            String startDate = request.getParameter("start_date");
            String endDate = request.getParameter("end_date");
            //room quantities
            String stdTCount = request.getParameter("std_t");
            String stdDCount = request.getParameter("std_d");
            String supTCount = request.getParameter("sup_t");
            String supDCount = request.getParameter("sup_d");
            //finance
            String cardNo = request.getParameter("card_no");
            String cardType = request.getParameter("card_type");
            String cardExp = request.getParameter("card_exp");
            //button val
            String editSubmit = request.getParameter("edit_submit");
            
            //find what we need to do
            //if the button has been pressed as there is a bref, we need to 
            //update
            try {
                Model model = null;
                if(!(Objects.isNull(editSubmit) && Objects.isNull(bookRef))) {
                    model = new Model();
                    Booking booking = model.BOOKINGS.getBooking(Integer.parseInt(bookRef));
                    //update any customer details
                    Customer c = booking.getCustomer();
                    //do not allow changes to name or ref
                    c.setEmail(custEmail);
                    c.setAddress(custAddress);
                    model.CUSTOMERS.updateCustomer(c);
                    
                    //now we want to work out what rooms are being added, what rooms
                    //are being removed
                    c.
                }

                //if a booking reference is set, we want to find details and display
                if (!Objects.isNull(bookRef)) {

                }

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/editbooking.jsp");
                dispatcher.forward(request, response);
            } catch (ModelException e) {
                //
            }
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
