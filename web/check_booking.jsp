<%-- 
    Document   : check_booking
    Created on : 29-Nov-2017, 21:54:05
    Author     : Hal
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/header.jspf" %>
    <div class="main-content">
    <h2>Booking</h2>
    <div class="flexCont">
        <%-- Print out errors --%>
        <%
            List<String> messages;
            if (request.getAttribute("messages") != null) {
                messages = (List<String>)request.getAttribute("messages");
            } else {
                messages = new ArrayList<>();
            }
            for(String m : messages) {
                String[] parts = m.split("#");
                String cssClass = "message-" + parts[0];
        %>
        <div class="flexItem <% out.print(cssClass); %>" onclick="$(this).remove();">
            <% out.print(parts[1]); %>
        </div>
        <%
            }
        %>
    </div>
    <form method="POST">
        <fieldset  class="content-bg">
            <legend>Update Your Booking</legend>
            <div class="form-card-container">
                <div class="form-card">
                    <input type="text" name="b_ref" id="booking_ref" placeholder="Booking Reference" class="form-spacing" required>
                    <button type="submit" value="submit">Show Booking</button>
                </div>
            </div>
        </fieldset>
    </form>
    </div>
<%@include file="/WEB-INF/footer.jspf" %>
