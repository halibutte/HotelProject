/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Staff;

import DataModel.Customer;
import DataModel.Booking;
import DataModel.Model;
import DataModel.ModelException;
import DataModel.Room;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gmc13udu
 */
public class BookingRoom extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            //getting parameters from the jsp pages
            String c_id = (String) request.getParameter("c_id");
            String std_t = (String) request.getParameter("std_t");
            String std_d = (String) request.getParameter("std_d");
            String sup_t = (String) request.getParameter("sup_t");
            String sup_d = (String) request.getParameter("sup_d");
            String c_in_date = (String) request.getParameter("c_in_date");
            String c_out_date = (String) request.getParameter("c_out_date");
            String name = (String) request.getParameter("name");
            String email = (String) request.getParameter("email");
            String address = (String) request.getParameter("address");
            String notes = (String) request.getParameter("notes");
            String cardtype = (String) request.getParameter("cardtype");
            String cardno = (String) request.getParameter("cardno");
            String cardexp = (String) request.getParameter("cardexp");
            //declaring and initialising variables to use in this servelet
            LocalDate Checkin = LocalDate.now();
            LocalDate Checkout = LocalDate.now();
            Integer StdTno = 0;
            Integer StdDno = 0;
            Integer SupTno = 0;
            Integer SupDno = 0;
            Integer CustID = null;
            //messages for the booking page
            String roomNo_msg = "Enter number of rooms";
            String dates_message = "Please enter/check dates";
            String selection_message = "Please change your selection";
            String proceed_message = "Preference available. You can proceed to enter your details and make a booking or search for other preferences";
            String expiry_alert = "Enter valid card expiry";
            //Linking variables in servelet to parameters entered on booking page
            if (!Objects.isNull(c_id)) {
                try {
                    CustID = Integer.parseInt(c_id);
                } catch (Exception e) {

                }
            }
            if (!Objects.isNull(c_in_date)) {
                try {
                    Checkin = LocalDate.parse(c_in_date);
                } catch (Exception e) {

                }
            }
            if (!Objects.isNull(c_out_date)) {
                try {
                    Checkout = LocalDate.parse(c_out_date);
                } catch (Exception e) {

                }
            }

            if (!Objects.isNull(std_t)) {
                try {
                    StdTno = Integer.parseInt(std_t);
                } catch (Exception e) {

                }
            }
            if (!Objects.isNull(std_d)) {
                try {
                    StdDno = Integer.parseInt(std_d);
                } catch (Exception e) {

                }
            }
            if (!Objects.isNull(sup_t)) {
                try {
                    SupTno = Integer.parseInt(sup_t);
                } catch (Exception e) {

                }
            }
            if (!Objects.isNull(sup_d)) {
                try {
                    SupDno = Integer.parseInt(sup_d);
                } catch (Exception e) {

                }
            }
            //Initialising and declaring customer details
            String Name = "";
            String Email = "";
            String Address = "";
            String Notes = "";
            String Cardtype = "";
            String Cardno = "";
            String Cardexp = "";

            
            //initialising messages for jsp page and cheacks
            String b_message = "";
            String s_message;
            String d_message = "";
            String stdt_message = "";
            String stdd_message = "";
            String supt_message = "";
            String supd_message = "";
            String c_message = "";
            //map to obtain room prices
            Map<String,Double> room_rates = new HashMap<>();
            //connecting to the database through a created class
            Model model = null;
            try {
                model = new Model();
                room_rates = model.ROOMS.getRates();
            } catch (ModelException e) {
                String errorMessage = "error#Cannot connect to databse";
            }
            //list of the room available depending on dates given
            List<Room> r_available;
            r_available = model.ROOMS.getRoomsAvailByDate(Checkin, Checkout);

            Integer nStdT = 0, nStdD = 0, nSupT = 0, nSupD = 0;
            //counting the rooms available for each type
            for (Room a : r_available) {
                switch (a.getRoomClass()) {
                    case "std_t":
                        nStdT++;
                        break;
                    case "std_d":
                        nStdD++;
                        break;
                    case "sup_t":
                        nSupT++;
                        break;
                    case "sup_d":
                        nSupD++;
                        break;
                    default:
                        break;
                }
            }
            //messages to prompt customer to enter rooms needed
            if (StdTno == 0 && StdDno == 0 && SupTno == 0 && SupDno == 0) {
                b_message = roomNo_msg;
            }
            //get a map of String (class) to amnt avail to pass to page for display
            Map<String,Long> countAvail = null;
            if(!Objects.isNull(c_in_date)) {
                countAvail = model.ROOMS.getCountRoomsAvailByDate(Checkin, Checkout);
            }
            //messages to let customer know if the rooms they want are not available and let them know what is avaiable for that period
            if (StdTno > nStdT) {
                stdt_message = "Not enough Standard twin rooms available. Number of rooms left for your chosen period: \n" + nStdT;
            }

            if (StdDno > nStdD) {
                stdd_message = "Not enough Standard double rooms available. Number of rooms left for your chosen period: \n" + nStdD ;
            }

            if (SupTno > nSupT) {
                supt_message = "Not enough Superior twin rooms available. Number of rooms left for your chosen period: \n" + nSupT ;
            }

            if (SupDno > nSupD) {
                supd_message = "Not enough Superior double rooms available. Number of rooms left for your chosen period: \n" + nSupD ;
            }
            //message to prompt customer to enter valid dates
            if (Checkout.isBefore(LocalDate.now()) || Checkin.isBefore(LocalDate.now()) || Checkin.isAfter(Checkout) || Checkin.isEqual(Checkout)) {
                d_message = dates_message;
            }
            //message to prompt customer to change selection given what they want is not available
            if (StdTno > nStdT || StdDno > nStdD || SupTno > nSupT || SupDno > nSupD) {
                s_message = selection_message;
            } else {
                //message letting customer know we have what they want and they can proceed to book
                s_message = proceed_message;
            }
            //retrieving customer details from the database given they provide a customer ID
            //minimal log in system which would need to be made more secure in a real world situation by a password or something
            //if customer changes their name or email after the information is retrieved a new customer will be created 
            //if they change anything else their exisiting customer details will be updated
            if (CustID != null) {
                //retrieving customer from database
                Customer customer = model.CUSTOMERS.getCustomer(CustID);

                if (customer != null) {

                    Name = customer.getName();
                    Email = customer.getEmail();
                    Address = customer.getAddress();
                    Cardtype = customer.getCardtype();
                    Cardexp = customer.getCardexp();
                    Cardno = customer.getCardno();
                    CustID = null;
                } else {
                    CustID = null;
                    //customer number doesn't exist
                }

            }
            //linking customer details to jsp
            if (!Objects.isNull(name)) {
                try {
                    Name = name;
                } catch (Exception e) {

                }
            }
            if (!Objects.isNull(email)) {
                try {
                    Email = email;
                } catch (Exception e) {

                }
            }
            if (!Objects.isNull(address)) {
                try {
                    Address = address;
                } catch (Exception e) {

                }
            }
            if (!Objects.isNull(notes)) {
                try {
                    Notes = notes;
                } catch (Exception e) {

                }
            }
            if (!Objects.isNull(cardtype)) {
                try {
                    Cardtype = cardtype;
                } catch (Exception e) {

                }
            }
            if (!Objects.isNull(cardno)) {
                try {
                    Cardno = cardno;
                } catch (Exception e) {

                }
            }
            if (!Objects.isNull(cardexp)) {
                try {
                    Cardexp = cardexp;
                } catch (Exception e) {

                }
            }

            String[] cardexpS1 = null;

            if (Cardexp.contains("/")) {
                cardexpS1 = Cardexp.split("/");   
            }
            

            //Statements to check if the card details are valid
            if(cardexpS1!=null){
            if (Integer.parseInt(cardexpS1[1]) > 99 || Integer.parseInt(cardexpS1[1]) < (LocalDate.now().getYear() % 100) || Integer.parseInt(cardexpS1[0]) < 1
            || Integer.parseInt(cardexpS1[0]) > 12 || !Cardexp.contains("/") || Cardexp.length() > 5) {
                c_message = expiry_alert;
            }
            if(LocalDate.now().getYear() % 100 == Integer.parseInt(cardexpS1[1])
                    && Integer.parseInt(cardexpS1[0])<LocalDate.now().getMonthValue() ){
                c_message = expiry_alert;
            }
            }

            Customer customer = new Customer(Name, Email, Address, Cardtype, Cardexp, Cardno);
            Customer customer1 = model.CUSTOMERS.getCustomer(Name, Email);
            //creating cutomer given they have valid card details
            if(!c_message.equals(expiry_alert)){
            if (customer1 == null) {

                try {
                   Customer customer2 =  model.CUSTOMERS.createCustomer(customer);
                   
                } catch (Exception e) {
                }
            }
            //updating customer given customer already exists and they changed something
            try {
                model.CUSTOMERS.updateCustomer(customer);
            } catch (Exception e) {
            }
            }
            //List of the rooms the customer wants
            List<Room> r_availableB = new ArrayList<>();
            
            //statements adding the rooms needed to list
            for(int t = 1;t<StdTno+1;t++){
                Room a = new Room();
                a.setRoomClass("std_t");
                r_availableB.add(a);
            }
            for(int t = 1;t<StdDno+1;t++){
                Room a = new Room();
                a.setRoomClass("std_d");
                r_availableB.add(a);
            }
            for(int t = 1;t<SupTno+1;t++){
                Room a = new Room();
                a.setRoomClass("sup_t");
                r_availableB.add(a);
            }
            for(int t = 1;t<SupDno+1;t++){
                Room a = new Room();
                a.setRoomClass("sup_d");
                r_availableB.add(a);
            }
         
       String requestJsp = "/bookingroom.jsp"  ;
       //make a booking given card details are valid and the make booking button is clicked
       if(request.getParameter("bButton")!=null && !c_message.equals(expiry_alert)){
            try {
                Booking b = model.BOOKINGS.makeBooking(customer, r_availableB, Checkin, Checkout,Notes);
                request.setAttribute("b_ref", b.getRef());
                request.setAttribute("room_con",r_availableB );
                requestJsp = "/Confirmation.jsp";
            } catch (Exception e) {
            }
       }
            //seeting attributes for the jsp page
            request.setAttribute("CustID", CustID);
            request.setAttribute("r_available", r_available);
            request.setAttribute("b_message", b_message);
            request.setAttribute("s_message", s_message);
            request.setAttribute("d_message", d_message);
            request.setAttribute("stdt_message", stdt_message);
            request.setAttribute("stdd_message", stdd_message);
            request.setAttribute("supt_message", supt_message);
            request.setAttribute("supd_message", supd_message);
            request.setAttribute("c_message", c_message);
            request.setAttribute("StdTno", StdTno);
            request.setAttribute("StdDno", StdDno);
            request.setAttribute("SupTno", SupTno);
            request.setAttribute("SupDno", SupDno);
            request.setAttribute("Checkin", Checkin);
            request.setAttribute("Checkout", Checkout);
            request.setAttribute("Name", Name);
            request.setAttribute("Email", Email);
            request.setAttribute("Address", Address);
            request.setAttribute("Notes", Notes);
            request.setAttribute("Cardtype", Cardtype);
            request.setAttribute("Cardexp", Cardexp);
            request.setAttribute("Cardno", Cardno);
            request.setAttribute("count_avail", countAvail);
            request.setAttribute("room_rates", room_rates);
            request.setAttribute("roomNo_msg", roomNo_msg);
            request.setAttribute("dates_message", dates_message);
            request.setAttribute("selection_message", selection_message);
            request.setAttribute("proceed_message", proceed_message);
            
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(requestJsp);
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
