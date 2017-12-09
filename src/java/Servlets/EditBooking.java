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
import DataModel.Room;
import DataModel.RoomBooking;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hal
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
            //Test implementation of an editbooking servlet
            //This implementation is agnostic about how you arrived here.
            //Will get get the bref, and display details, or process update
            //if update request has been submitted
            String bref = request.getParameter("b_ref");
            String btnClicked = request.getParameter("btn_update");
            List<String> messages = new ArrayList<>();

            Booking booking = null;
            Model model = null;
            Map<String,Long> countAvail = null;
            boolean doneUpdate = false;
            String responsePage = "/check_booking.jsp";

            try {
                if(!Objects.isNull(btnClicked) || !Objects.isNull(bref)) {
                    model = new Model();
                    //get the number of rooms available for these dates
                    int int_bref = Integer.parseInt(bref);
                    //get the booking
                    booking = model.BOOKINGS.getBooking(int_bref);
                    if(Objects.isNull(booking)) {
                        throw new ModelException("Could not find booking for reference " + int_bref);
                    }
                    request.setAttribute("booking", booking);
                    LocalDate checkin, checkout;
                    try {
                        checkin = LocalDate.parse(request.getParameter("checkin"));
                        checkout = LocalDate.parse(request.getParameter("checkout"));
                    } catch (Exception e) {
                        checkin = booking.getRooms().get(0).getCheckin();
                        checkout = booking.getRooms().get(0).getCheckout();
                    }
                    
                    //if booking has already commenced, do not allow editing
                    if(checkin.isBefore(LocalDate.now()) || checkin.isEqual(LocalDate.now())) {
                        throw new ModelException("Sorry, you can only change your booking up to one day before checkin");
                    }
                    
                    try {
                        Map<String,Long> checkAvail = model.ROOMS.getCountRoomsAvailByDate(checkin, checkout, int_bref);
                        request.setAttribute("count_avail", checkAvail);
                        Map<String,Double> roomRates = model.ROOMS.getRates();
                        request.setAttribute("room_rates", roomRates);
                    } catch(Exception e) {
                        messages.add("error#Unable to retrieve available rooms for selected period");
                    }
                    
                    //try proceesing action
                    if(!Objects.isNull(btnClicked)) {
                        //an update has been requested
                        //set the rooms which were requested
                        responsePage = "/edit_booking.jsp";
                        if(validateUpdate(request, messages, model)) {                       
                            //try updating booking
                            Booking updated = updateBooking(request, model);
                            request.setAttribute("booking", updated);
                            request.setAttribute("rooms_requested", mapBookedRooms(updated, model));
                            //try updating customer details
                            Customer cust = updated.getCustomer();
                            cust.setAddress(request.getParameter("address"));
                            cust.setCardexp(request.getParameter("cardexp"));
                            cust.setCardno(request.getParameter("cardno"));
                            cust.setCardtype(request.getParameter("cardtype"));
                            cust.setEmail(request.getParameter("email"));
                            model.CUSTOMERS.updateCustomer(cust);
                            doneUpdate = true;
                            messages.add("confirm#Booking succesfully updated, new details shown");
                        } else {
                            //set booking details to the posted info
                            //so it displays if there's an error
                            //set the rooms requested to those requested
                            request.setAttribute("rooms_requested", mapRequest(request));
                            Customer c = booking.getCustomer();
                            c.setAddress(request.getParameter("address"));
                            c.setCardexp(request.getParameter("cardexp"));
                            c.setCardno(request.getParameter("cardno"));
                            c.setCardtype(request.getParameter("cardtype"));
                            c.setEmail(request.getParameter("email"));
                        }
                    } else if (!Objects.isNull(bref)) {
                        //no update requested, but ref provided
                        //cast bref to int
                        int int_ref = Integer.parseInt(bref);
                        booking = model.BOOKINGS.getBooking(int_ref);
                        //add as attribute
                        request.setAttribute("booking", booking);
                        request.setAttribute("rooms_requested", mapBookedRooms(booking, model));
                        responsePage = "/edit_booking.jsp";
                    }
                }
            } catch (ModelException e) {
                messages.add("error#"+e.getMessage());
            }
            
            request.setAttribute("messages", messages);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(responsePage);
            dispatcher.forward(request, response);
        }
    }
    
    protected Booking updateBooking(HttpServletRequest request, Model model) throws ModelException {
        Booking booking = null;
        //get values the user wants to edit to
        //room types into a map to make easier to loop
        Map<String,Integer> roomsRequested = new HashMap<>();
        String[] roomTypes = {"std_t", "std_d", "sup_t", "sup_d"};
        for(String type : roomTypes) {
            roomsRequested.put(type, Integer.parseInt(request.getParameter(type)));
        }
        //checkin/out
        LocalDate checkin = LocalDate.parse(request.getParameter("checkin"));
        LocalDate checkout = LocalDate.parse(request.getParameter("checkout"));
        //booking ref
        int bref = Integer.parseInt(request.getParameter("b_ref"));
        //construct a list of rooms to book
        List<Room> roomsToBook = new ArrayList<>();
        for(Map.Entry<String,Integer> e : roomsRequested.entrySet()) {
            for(int i = 0; i < e.getValue(); i++) {
                Room r = new Room();
                r.setRoomClass(e.getKey());
                roomsToBook.add(r);
            }
        }

        //try to update the booking
        booking = model.BOOKINGS.updateBooking(bref, roomsToBook, checkin, checkout);
        return booking;
    }
    
    protected Map<String,Integer> mapRequest(HttpServletRequest request) {
        Map<String,Integer> roomsRequested = new HashMap<>();
        String[] roomTypes = {"std_t", "std_d", "sup_t", "sup_d"};
        for(String type : roomTypes) {
            try {
                roomsRequested.put(type, Integer.parseInt(request.getParameter(type)));
            } catch (Exception e) {
                roomsRequested.put(type, 0);
            }
        }
        return roomsRequested;
    }
    
    protected Map<String,Integer> mapBookedRooms(Booking booking, Model model) {
        List<Room> rooms = model.ROOMS.getAllRooms();
        Map<String, Integer> map = booking.getRooms().stream()
                .map((RoomBooking r) -> {
                    Optional<String> room = rooms.stream()
                            .filter(t -> r.getRoomNo().equals(t.getNo()))
                            .map(t -> t.getRoomClass())
                            .findFirst();
                    if(room.isPresent()) {
                        return room.get();
                    } else {
                        return null;
                    }
                })
                .filter(p -> !Objects.isNull(p))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));
        String[] classes = {"std_t", "std_d", "sup_t", "sup_d"};
        for(String rclass : classes) {
            if(!map.containsKey(rclass)) {
                map.put(rclass, 0);
            }
        }
        return map;
    }
    
    protected boolean validateUpdate(HttpServletRequest request, List<String> messages, Model model) {
        //validate all the fields coming in for update request
        boolean valid = true;
        //proper room name map
        Map<String, String> nameLookup = new HashMap<>();
        nameLookup.put("std_t", "Standard Twin");
        nameLookup.put("std_d", "Standard Double");
        nameLookup.put("sup_t", "Superior Twin");
        nameLookup.put("sup_d", "Superior Double");
        try {
            //validate dates
            LocalDate checkin = null; 
            LocalDate checkout = null;
            Integer int_bref = null;
            
            //cast bref
            try {
                int_bref = Integer.parseInt(request.getParameter("b_ref"));
            } catch (Exception e) {
                messages.add("error#Check booking reference is entered correctly");
                valid = false;
            }
            
            //Dates
            try {
                checkin = LocalDate.parse(request.getParameter("checkin"));
                checkout = LocalDate.parse(request.getParameter("checkout"));
                if(checkin.isBefore(LocalDate.now())) {
                    messages.add("error#Checkin cannot be in the past");
                    valid = false;
                }
                if(checkout.isBefore(checkin) || checkout.isEqual(checkin)) {
                    messages.add("error#Checkout must be at least one day after checkin");
                    valid = false;
                }
            } catch (Exception e) {
                messages.add("error#Please enter dates in correct format");
            }
            
            //check all room request are numeric, and have asked for at least one room
            try {
                Map<String, Integer> requests = mapRequest(request);
                //has more than one room been requested?
                int sum = requests.values().stream().mapToInt(v -> v.intValue()).sum();
                if(sum <= 0) {
                    messages.add("error#Must request at least one room");
                    valid = false;
                }
                //check if the rooms requested are available
                if(!(Objects.isNull(checkin)||Objects.isNull(checkout)||Objects.isNull(int_bref))) {
                    Map<String, Long> avail = model.ROOMS.getCountRoomsAvailByDate(checkin, checkout, int_bref);
                    //loop through requests, check avail >=
                    for(Map.Entry<String,Integer> req : requests.entrySet()) {
                        int avail_count = (avail.get(req.getKey()) == null ? 0 : avail.get(req.getKey()).intValue());
                        if(avail_count < req.getValue()) {
                            messages.add("error#Not enough " + nameLookup.get(req.getKey()) + " rooms, only " + avail_count + " available");
                            valid = false;
                        }
                    }
                }                
            } catch (Exception e) {
                messages.add("error#Error checking room requests");
            }
            
            //check customer details
            try {
                //do not allow editting name
                String email = request.getParameter("email");
                String cardno = request.getParameter("cardno");
                String cardexp = request.getParameter("cardexp");
                String cardtype = request.getParameter("cardtype");
                
                //validate email
                if(!email.matches(".*@.*\\..*")) {
                    messages.add("error#Enter email in format xx@yy.zz");
                    valid = false;
                }
                
                if(!cardno.matches("\\d{13,19}")) {
                    messages.add("error#Enter a valid card number (between 13 and 19 digits)");
                    valid = false;
                }
                
                //validate card exp
                if(cardexp.matches("\\d{2}/\\d{2}")) {
                    //convert to date
                    String[] parts = cardexp.split("/");
                    try {
                        LocalDate expDate = LocalDate.of(Integer.parseInt("20"+parts[1]), Integer.parseInt(parts[0]), 1);
                        LocalDate thisMonth = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
                        if(expDate.isBefore(thisMonth)) {
                            valid = false;
                            messages.add("error#Card has expired, please enter alternate payment details");
                        }
                    } catch (Exception e) {
                        valid = false;
                        messages.add("error#Expiry date not recognised, enter in format MM/YY");
                    }
                } else {
                    messages.add("error#Enter card expiry in format MM/YY");
                    valid = false;
                }
                
                //validate card type
                if(!(cardtype.equals("V")||cardtype.equals("MC")||cardtype.equals("A"))) {
                    valid = false;
                    messages.add("error#Card type invalid");
                }
            } catch (Exception e) {
                messages.add("error#Error checking customer details");
            }
        } catch (Exception e) {
            messages.add("error#Error reading input for update");
            valid = false;
        }
        return valid;
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
