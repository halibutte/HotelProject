<%-- 
    Document   : Confirmation
    Created on : 06-Dec-2017, 10:21:57
    Author     : jdk17aku
--%>

<%@page import="java.time.LocalDate"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map"%>
<%@page import="DataModel.Room"%>
<%@page import="java.util.List"%>
<%@page import="DataModel.RoomBooking"%>
<%@page import="DataModel.ModelException"%>
<%@page import="DataModel.Booking"%>
<%@page import="DataModel.Model"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/header.jspf" %>
<%  Integer bookingRef = (Integer)request.getAttribute("b_ref");
    Integer cus_id;
    String cus_name;
    String cus_email;
    String booking_notes;
    Double b_cost;
    LocalDate in_d; 
    LocalDate out_d; 
    String string_Std_T = "";
    String string_Std_D = "";
    String string_Sup_T = "";
    String string_Sup_D = "";
    
    Model model = null;
            try {
                model = new Model();
                //room_rates = model.ROOMS.getRates();
            } catch (ModelException e) {
                String errorMessage = "error#Cannot connect to databse";
            }
            Booking b = new Booking();
            //Pull booking ref from database
            b = model.BOOKINGS.getBooking(bookingRef);//b_ref
            //Pull Customer ID, Name, Email, Notes and Cost.
            cus_id = b.getCustomer().getNo();
            cus_name = b.getCustomer().getName();
            cus_email = b.getCustomer().getEmail();
            booking_notes = b.getNotes();
            b_cost = b.getCost();
            //Pull customer dates from Database
            in_d = b.getRooms().get(0).getCheckin();
            out_d = b.getRooms().get(0).getCheckout(); 
            List<Room> rm = (List<Room>)request.getAttribute("room_con");//prev page
            Integer nStdT = 0, nStdD = 0, nSupT = 0, nSupD = 0;
            for(Room c: rm){
                
            //If room type is registered, add number to quantity.    
                if(c.getRoomClass().equals("std_t")){
                nStdT++;};
                if(c.getRoomClass().equals("std_d")){
                nStdD++;};
                if(c.getRoomClass().equals("sup_t")){
                nSupT++;};
                if(c.getRoomClass().equals("sup_d")){
                nSupD++;};

            }
           
           //Printing label and number of rooms book of these type. 
           if(nStdT>0){
           string_Std_T = "Standard Twin: "+nStdT;}
            
           if(nStdD>0){
           string_Std_D = "Standard Double: "+nStdD;}
            
           if(nSupT>0){
           string_Sup_T = "Superior Twin: "+nSupT;}
            
           if(nSupD>0){
           string_Sup_D = "Superior Double: "+nSupD;}
%>
<div class="main-content">
        <h1>Thank you!</h1>
        <div class="content">
            <p>Thank you for booking your stay with us, the following information has been sent
            to the email address provided. <br> If you need check to your booking details you can do so by clicking 
            <a href="./EditBooking"><!--link to booking page--><u>here</u></a> and supplying your booking number and email. Didn't receive your email confirmation? Call or email us! <br><br>
            Telephone: (+44) 01603 123456 <br>
            Email: Bookings@HeartacheHotel.co.uk
            </p>
        </div>
        <div class="content">
            <h1>Booking Complete!</h1> <br>
            <fieldset><legend>Email Confirmation</legend>
                <!--Print Customer Name-->
                Dear <%out.print(cus_name);%> 
                
                This is an email confirmation for your order, please retain this information for future reference. <br><br>
                Your booking has been received and reserved. <br> <br>
                <h3>Order details:</h3>
                <!--Print Customer ID, Email, Arrival/Departure, Cost & Notes.-->
                Customer ID: <% out.print(cus_id); %><br>
                Email Address: <% out.print(cus_email); %><br>
                Booking Reference number: <% out.print(b.getRef()); %><br>
                Checkin Date: <% out.print(in_d); %><br>
                Checkout Date: <% out.print(out_d); %><br>
                Total cost: <% out.print(b_cost); %><br>
                Notes: <% out.print(booking_notes); %><br><br>
                The following rooms have been reserved for you:
                <!--Print rooms book, if no room is booked room will not be displayed-->
                <ul>
                    <% if(nStdT > 0) { %>
                    <li><% out.print(string_Std_T); %></li>
                    <% } %>   
                    <% if(nStdD > 0) { %>
                    <li><% out.print(string_Std_D); %></li>
                    <% } %>   
                    <% if(nSupT > 0) { %>
                    <li><% out.print(string_Sup_T); %></li>
                    <% } %>   
                    <% if(nSupD > 0) { %>
                    <li><% out.print(string_Sup_D); %></li>
                    <% } %>   
                </ul>
                    <br>Thank you from Heartache!
                    <p> From all the staff of The Heartache Hotel, we look forward to seeing you soon!</p>
            </fieldset>
        </div>
    </div>
<%@include file="/WEB-INF/footer.jspf" %>