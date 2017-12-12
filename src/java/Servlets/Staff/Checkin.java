/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Staff;

import DataModel.Booking;
import DataModel.Customer;
import DataModel.Model;
import DataModel.ModelException;
import DataModel.Room;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hal
 */
public class Checkin extends HttpServlet {

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
            //list to add messages into, to be displayed in sidebar
            List<String> messages = new ArrayList<>();
            //get some parameters
            String forDate = (String)request.getParameter("forDate");
            String roomNo = (String)request.getParameter("roomNo");
            String roomStatus = (String)request.getParameter("roomStatus");
            String actType = (String)request.getParameter("actType");
            String balance = (String)request.getParameter("balOutstanding");
            String bref = (String)request.getParameter("bookRef");
            String cardExp = (String)request.getParameter("cardExp");
            String cardType = (String)request.getParameter("cardType");
            String cardNum = (String)request.getParameter("cardNum");
            //if the date is not null use date, otherwise use today
            LocalDate viewDate = LocalDate.now();
            if(!Objects.isNull(forDate)) {
                try {
                    viewDate = LocalDate.parse(forDate);
                } catch (Exception e) {
                    //leave as today
                }
            }
            
            Model model = null;
            try {
                model = new Model();
            } catch (ModelException e) {
                messages.add("error#Cannot connect to databse");
            }
            
            //update a room status if this was requested
            if(!Objects.isNull(roomNo)) {
                try {
                    Room r = model.ROOMS.getRoom(Integer.parseInt(roomNo));
                    //only attempt update if the database status is differen to
                    //status from form
                    if(!r.getStatus().equals(roomStatus)) {
                        r.setStatus(roomStatus);
                        model.ROOMS.updateRoom(r);
                        messages.add("confirm#Room " + r.getNo() + " status updated to " + r.getLongStatus());
                    }
                } catch (Exception e) {
                    messages.add("error#Failed to update room status");
                }
            }
           
            //update booking balance if request
            if(!Objects.isNull(actType)) {
                if(actType.equals("checkout")) {
                    Booking b = model.BOOKINGS.getBooking(Integer.parseInt(bref));
                    //update the customers payment details
                    Customer c = b.getCustomer();
                    //only attempt update if any details from form different to db
                    if(!(c.getCardno().equals(cardNum) && c.getCardtype().equals(cardType) && c.getCardexp().equals(cardExp))) {
                        c.setCardno(cardNum);
                        c.setCardtype(cardType);
                        c.setCardexp(cardExp);
                        model.CUSTOMERS.updateCustomer(c);
                        messages.add("confirm#Customer " + c.getName() + "'s payment details updated");
                    }
                    //now do payment on booking
                    Double pay = 0.0;
                    try {
                        pay = Double.parseDouble(balance);
                        if(pay > 0) {
                            model.BOOKINGS.takePayment(b, pay);
                            messages.add("confirm#Payment of " + NumberFormat.getCurrencyInstance().format(pay) + " processed for booking " + b.getRef());
                        }
                    } catch (ModelException e) {
                        //unable to parse payment amount
                        messages.add("error#" + e.getMessage());
                    } catch (Exception e) {
                        messages.add("error#Unable to process payment");
                    }
                }
            }
            
            //get a map of the expected checkins and checkouts
            //the key is the rooms they will be in, value is the booking
            //this roombooking is related to
            Map<Room, Booking> checkins = model.ROOMS.getExpectedArrivals(viewDate);
            Map<Room, Booking> checkouts = model.ROOMS.getExpectedDepartures(viewDate);
            //set some attributes for the jsp
            request.setAttribute("checkins", checkins);
            request.setAttribute("checkouts", checkouts);
            request.setAttribute("viewdate", viewDate);
            request.setAttribute("messages", messages);
            
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Staff/checkin.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            //e.printStackTrace(out);
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
