<%-- 
    Document   : Confirmation
    Created on : 06-Dec-2017, 10:21:57
    Author     : jdk17aku
--%>
<%@page import="java.text.DecimalFormat"%>
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
    DecimalFormat df = new DecimalFormat("###,##0.00");
            try {
                model = new Model();
                //room_rates = model.ROOMS.getRates();
            } catch (ModelException e) {
                String errorMessage = "error#Cannot connect to databse";
            }
            Booking b = new Booking();
            b = model.BOOKINGS.getBooking(bookingRef);
            cus_id = b.getCustomer().getNo();
            cus_name = b.getCustomer().getName();
            cus_email = b.getCustomer().getEmail();
            booking_notes = b.getNotes();
            b_cost = b.getCost();
            in_d = b.getRooms().get(0).getCheckin();
            out_d = b.getRooms().get(0).getCheckout();
            List<Room> rm = (List<Room>)request.getAttribute("room_con");
            Integer nStdT = 0, nStdD = 0, nSupT = 0, nSupD = 0;
            for(Room c: rm){

                if(c.getRoomClass().equals("std_t")){
                nStdT++;};
                if(c.getRoomClass().equals("std_d")){
                nStdD++;};
                if(c.getRoomClass().equals("sup_t")){
                nSupT++;};
                if(c.getRoomClass().equals("sup_d")){
                nSupD++;};

            }
           
            
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
            to the email address provided. <br> If you need check to your booking details you can do so by  
            <a href="./EditBooking" target="_blank">clicking here</a> and supplying your booking number and email. 
            <br>You will only be able to change your booking up to the day before you check in. After that you will not be able to change or cancel your booking.
            <br>Didn't receive your email confirmation? Call or email us! <br><br>
            Telephone: (+44) 01603 123456 <br>
            Email: Bookings@HeartacheHotel.co.uk
            </p>
        </div>
        <div class="content">
            <h1>Booking Complete!</h1> <br>
            <fieldset><legend>Email Confirmation</legend>
                Dear <%=cus_name%>,
                <br>
                <br>
                This is an email confirmation for your booking, please retain this information for future reference. <br><br>
                Your booking has been received and reserved. <br> <br>
                <h2>Order details:</h2>
                Customer ID: <% out.print(cus_id); %><br>
                Booking Reference: <% out.print(b.getRef()); %><br>
                Email Address: <% out.print(cus_email); %><br>
                Check in: <% out.print(in_d); %><br>
                Check out: <% out.print(out_d); %><br>
                Total Cost: £<% out.print(df.format(b_cost)); %><br>
                Notes: <% out.print(booking_notes); %><br><br>
                The following rooms have been reserved for you:
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
                To change or cancel your booking, <a href="./EditBooking" target="_blank">click here</a>. You will be asked for your email address and booking reference.
                    <br>Thank you from Heartache!
                    <p> From all the staff of The Heartache Hotel, we look forward to seeing you soon!</p>
            </fieldset>
        </div>
    </div>
<%@include file="/WEB-INF/footer.jspf" %>