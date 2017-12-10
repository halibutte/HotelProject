/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Staff;

import DataModel.BillableItem;
import DataModel.BilledItem;
import DataModel.Booking;
import DataModel.Customer;
import DataModel.Model;
import DataModel.ModelException;
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
            String actType = request.getParameter("act_type");
            //add item parameters
            String itemCode = request.getParameter("item_code");
            String itemDesc = request.getParameter("item_desc");
            String itemPrice = request.getParameter("item_price");
            String itemBref = request.getParameter("item_bref");
            //take payment parameters
            String payNum = request.getParameter("card_num");
            String payType = request.getParameter("card_type");
            String payExp = request.getParameter("card_exp");
            String payBref = request.getParameter("card_bref");
            String payAmnt = request.getParameter("card_amnt");
            //remove item parameters
            String remId = request.getParameter("remove_item");
            
            LocalDate date = LocalDate.now();
            
            
            try {
                Model model = new Model();
                try {
                    date = LocalDate.parse(viewDate);
                } catch (Exception e) {
                    //leave as now
                }
                
                //try processing any actions received
                if(!Objects.isNull(actType)) {
                    //adding an item to the bill
                    if(actType.equals("add")) {
                        if(addItem(itemCode, itemDesc, itemPrice, itemBref, model)) {
                            request.setAttribute("highlight_bref", itemBref);
                            messages.add("confirm#Added item to bill");
                        } else {
                            messages.add("error#Failed adding item to bill");
                        }
                    }
                    
                    //removing an item from the bill
                    if(actType.equals("remove")) {
                        if(removeItem(remId, model)) {
                            messages.add("confirm#Deleted item from bill");
                            request.setAttribute("highlight_bref", request.getParameter("item_bref"));
                        } else {
                            messages.add("error#Failed when trying to delete item from bill");
                        }
                    }
                    
                    //taking some payment
                    if(actType.equals("pay")) {
                        try {
                            boolean accepted = takePayment(payBref, payAmnt, payNum, payExp, payType, model);
                            request.setAttribute("highlight_bref", payBref);
                            messages.add("confirm#Payment accepted");
                        } catch (ModelException e) {
                            messages.add("error#"+e.getMessage());
                        }
                    }
                }
                
                //get info to pass to JSP
                //get all the current bookings
                bookings = model.BOOKINGS.getAllOccupants(date);
                items = model.BILLABLES.getAllBillableItems();
            } catch (ModelException e) {
                messages.add("error#Problem connecting to database");
            }
            
            //forward to JSP for display
            request.setAttribute("messages", messages);
            request.setAttribute("bookings", bookings);
            request.setAttribute("items", items);
            request.setAttribute("view_date", date);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Staff/billing.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    protected boolean addItem(String code, String desc, String price, String bref, Model model) {
        //cast to correct types
        int castBref = Integer.parseInt(bref);
        double castPrice = Double.parseDouble(price);
        try {
            BilledItem bi = model.BILLABLES.addBilledItem(castBref, code, desc, castPrice);
            return true;
        } catch (ModelException e) {
            return false;
        }
    }
    
    protected boolean removeItem(String id, Model model) {
        //cast to right types
        int castId = Integer.parseInt(id);
        return model.BILLABLES.deleteBilledItem(castId);
    }
    
    protected boolean takePayment(String bref, String amount, String cardNo, String cardExp, String cardType, Model model)
    throws ModelException {
        //cast to correct types
        int castBref = Integer.parseInt(bref);
        double castAmount = Double.parseDouble(amount);
        
        //retrieve booking object
        Booking booking = model.BOOKINGS.getBooking(castBref);
        
        //update customer payment details
        Customer cust = booking.getCustomer();
        cust.setCardexp(cardExp);
        cust.setCardno(cardNo);
        cust.setCardtype(cardType);
        
        //try update then pay
        model.CUSTOMERS.updateCustomer(cust);
        model.BOOKINGS.takePayment(booking, castAmount);
        return true;            
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
