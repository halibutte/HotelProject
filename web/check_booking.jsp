<%-- 
    Document   : check_booking
    Created on : 29-Nov-2017, 21:54:05
    Author     : Hal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/header.jspf" %>
    <div class="main-content">
   <h2>Booking</h2>
    <form method="POST">
        <fieldset  class="content-bg">
            <legend>Check My Booking</legend>
            <div class="form-card-container">
                <div class="form-card">
                    <input type="text" name="email" id="email" class="form-spacing" placeholder="Email Address" required>
                    <input type="text" name="booking_ref" id="booking_ref" placeholder="Booking Reference" class="form-spacing" required>
                    <button type="submit" value="submit">Show Booking</button>
                </div>
            </div>
        </fieldset>
    </form>
    </div>
<%@include file="/WEB-INF/footer.jspf" %>
