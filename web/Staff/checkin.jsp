<%-- 
    Document   : checkin
    Created on : 25-Nov-2017, 14:15:25
    Author     : Hal
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="DataModel.Customer"%>
<%@page import="DataModel.Booking"%>
<%@page import="DataModel.Room"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="staff.css">
    </head>
    <body>
        <div class="main-container">
            
        <div class="col-minwidth">
            <div class="flexItem">
                <h4>View Date</h4>
                <form method="GET">
                    <input type="date" name="forDate" value="<% out.print(request.getAttribute("viewdate")); %>" class="form-spacing">
                    <button type="submit" class="button">Submit</button>
                </form>
            </div>
        </div>
                    
        <div class="col-half">
        <h2>Expected Arrivals</h2>
        <div class="flexCont">
            <%  //iterate over checkins and put in boxes
                Map<Room, Booking> map = (Map<Room, Booking>)request.getAttribute("checkins");
                for(Room r : map.keySet()) {
            %>
            <div class="flexItem">
                <h4>Room <% out.print(r.getNo()); %></h4>
                <div class="form-spacing-small">
                <% 
                Booking b = map.get(r);
                Customer c = b.getCustomer();
                out.print(c.getName()); %>
                <br>
                </div>
                <div class="form-spacing">
                Booking <% out.print(b.getRef()); %>
                </div>
                <div>
                    <form method="POST">
                        <input type="hidden" name="roomNo"  value="<% out.print(r.getNo()); %>"/>
                        <input type="hidden" name="actType"  value="checkin">
                        <div class="form-spacing">
                        <select name="roomStatus">
                            <option value="O"<% if(r.getStatus().equals("O")) { out.print(" selected"); } %>>Occupied</option>
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
        <h2>Expected Departures</h2>
        <div class="flexCont">
            <%  //iterate over checkins and put in boxes
                map = (Map<Room, Booking>)request.getAttribute("checkouts");
                for(Room r : map.keySet()) {
            %>
            <div class="flexItem">
                <h4>Room <% out.print(r.getNo()); %></h4>
                <div class="form-spacing-small">
                <% 
                Booking b = map.get(r);
                Customer c = b.getCustomer();
                out.print(c.getName() + " (" + b.getRef() + ")"); %>
                </div>
                <div class="form-spacing-small">
                Outstanding Balance
                </div>
                <div class="form-spacing">
                £<% out.print(b.getOutstandingString()); %>
                </div>
                <div>
                    <form method="POST">
                        <input type="hidden" name="roomNo"  value="<% out.print(r.getNo()); %>">
                        <input type="hidden" name="actType"  value="checkout">
                        <input type="hidden" name="bookRef"  value="<% out.print(b.getRef()); %>">
                        <div class="form-spacing">
                        <select name="roomStatus">
                            <option value="O"<% if(r.getStatus().equals("O")) { out.print(" selected"); } %>>Occupied</option>
                            <option value="C"<% if(r.getStatus().equals("C")) { out.print(" selected"); } %>>Checked Out</option>
                            <option value="X"<% if(r.getStatus().equals("X")) { out.print(" selected"); } %>>Unavailable</option>
                            <option value="A"<% if(r.getStatus().equals("A")) { out.print(" selected"); } %>>Available</option>
                        </select>
                        </div>
                        <div class="form-spacing-small">Payment Taken</div>
                        <input type="number" name="balOutstanding" value="0.00" class="form-spacing" step="0.01" min="0" max="<% out.print(b.getOutstanding()); %>">
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
