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
    String cus_name;
    String cus_email;
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
            b = model.BOOKINGS.getBooking(bookingRef);//b_ref
            cus_name = b.getCustomer().getName();
            cus_email = b.getCustomer().getEmail();
            b_cost = b.getCost();
            in_d = b.getRooms().get(0).getCheckin();
            out_d = b.getRooms().get(0).getCheckout();
            List<Room> rm = (List<Room>)request.getAttribute("room_con");//prev page
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
           string_Std_D = "Standard Twin: "+nStdD;}
            
           if(nSupT>0){
           string_Sup_T = "Standard Twin: "+nSupT;}
            
           if(nSupD>0){
           string_Sup_D = "Standard Twin: "+nSupD;}
%>
<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Confirmation Page</title>
    </head>
    <body>
        <br><h1>Update Completed!</h1>
        <div class="content">
            <p>Thank you for booking your stay with us, the following information has been sent
            to the email address provided. <br> If you need check to your booking details you can do so by clicking 
            <a href="./EditBooking"><u>here</u> </a> and supplying your booking number and email. Didn't receive your email confirmation? Call or email us! <br><br>
            Telephone: (+44) 01603 123456 <br>
            Email: Bookings@HeartacheHotel.co.uk
            </p>
        </div>
        <div class="content">
            <fieldset><legend>Email Confirmation</legend>
                
                
                Dear <%out.print(cus_name);%>
                
                This is an email confirmation for your order, please retain this information for future reference. <br><br>
                Your booking update has been received and reserved. <br> <br>
                <h7>Order details:</h7><br><br>
                <h8>Email Address: <% out.print(cus_email); %></h8><br>
                <h8>Reference number: <% out.print(b.getRef()); %> </h8><br>
                <h8>Checkin Date: <% out.print(in_d); %> </h8><br>
                <h8>Checkout Date: <% out.print(out_d); %> </h8><br><br>
                
                <h8>The following rooms are now been reserved for you: </h8>
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
        
              
                    <br><H9>Thank you from Heartache!</H9>
                    <p> From all the staff of The Heartache Hotel, we look forward to seeing you soon!</p>
    
            </fieldset>
            
        </div>
    </body>
<%@include file="/WEB-INF/footer.jspf" %>