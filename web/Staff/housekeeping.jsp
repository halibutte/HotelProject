<%-- 
    Document   : checkin
    Created on : 25-Nov-2017, 14:15:25
    Author     : Hal
--%>

<%@page import="java.util.List"%>
<%@page import="DataModel.Customer"%>
<%@page import="DataModel.Booking"%>
<%@page import="DataModel.Room"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width">
        <title>Housekeeping</title>
        <link rel="stylesheet" type="text/css" href="staff.css">
        <script src="jquery.min.js"></script>
        <script src="housekeeping.js"></script>
    </head>
    <body>
        <%@include file="staff_header.jsp" %>
        <div class="main-container">
        <div class="col-minwidth" id="message_div">
            <h2>Messages</h2>
            <div class="flexCont">
                <% List<String> msgs = (List<String>)request.getAttribute("messages");
                for(String s : msgs) {
                    String[] arr = s.split("#");
                %>
                <div class="flexItem message-<% out.print(arr[0]); %>" onclick="removeMessage(this);">
                    <% out.print(arr[1]); %>
                </div>
                <% }%>   
            </div>
        </div>
            
        <div class="col-half">
        <h2>Checked Out</h2>
        <div class="flexCont">
            <%  //iterate over checkins and put in boxes
                List<Room> checkedout = (List<Room>)request.getAttribute("statusC");
                for(Room r : checkedout) {
            %>
            <div class="flexItem">
                <h4>Room <% out.print(r.getNo()); %></h4>
                <div>
                    <form method="POST">
                        <input type="hidden" name="roomNo"  value="<% out.print(r.getNo()); %>"/>
                        <div class="form-spacing">
                        <select name="roomStatus">
                            <option value="C"<% if(r.getStatus().equals("C")) { out.print(" selected"); } %>>Checked Out</option>
                            <option value="X"<% if(r.getStatus().equals("X")) { out.print(" selected"); } %>>Unavailable</option>
                            <option value="A"<% if(r.getStatus().equals("A")) { out.print(" selected"); } %>>Available</option>
                        </select>
                        </div>
                        
                        <button type="submit" class="button" name="submit">Submit</button>
                    </form>
                </div>
            </div>
            <% } %>
        </div>
        </div>
        
        <div class="col-half">
        <h2>Unavailable</h2>
        <div class="flexCont">
            <%  //iterate over checkins and put in boxes
                List<Room> unavail = (List<Room>)request.getAttribute("statusX");
                for(Room r : unavail) {
            %>
            <div class="flexItem">
                <h4>Room <% out.print(r.getNo()); %></h4>
                <div>
                    <form method="POST">
                        <input type="hidden" name="roomNo"  value="<% out.print(r.getNo()); %>">
                        <div class="form-spacing">
                        <select name="roomStatus">
                            <option value="X"<% if(r.getStatus().equals("X")) { out.print(" selected"); } %>>Unavailable</option>
                            <option value="A"<% if(r.getStatus().equals("A")) { out.print(" selected"); } %>>Available</option>
                        </select>
                        </div>
                        <button type="submit" class="button" name="submit">Submit</button>
                    </form>
                </div>
            </div>
            <% }%>
        </div>
        </div>
        
        </div>
    </body>
</html>
