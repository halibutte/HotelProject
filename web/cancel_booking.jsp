<%-- 
    Document   : cancel_booking
    Created on : 11-Dec-2017, 15:58:08
    Author     : x3041557
--%>

<%@page import="DataModel.Booking"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/header.jspf" %>
<% Booking b = (Booking)request.getAttribute("booking"); %>
<div class="main-content">
    <h2>Booking Cancelled</h2>
    <div class="content large-text">
        We have cancelled your booking (reference <%=b.getRef()%>). We're sorry to hear you won't 
        be staying with us at the Heartache Hotel this time, but hope you choose 
        us in the future.
    </div>
</div>
<%@include file="/WEB-INF/footer.jspf" %>
