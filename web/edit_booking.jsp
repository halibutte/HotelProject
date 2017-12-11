<%-- 
    Document   : Booking
    Created on : 28-Nov-2017, 16:30:52
    Author     : gmc13udu
--%>


<%@page import="java.time.LocalDate"%>
<%@page import="DataModel.Booking"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Objects"%>
<%@page import="java.util.Map"%>
<%@page import="DataModel.ModelException"%>
<%@page import="DataModel.Model"%>
<%@page import="DataModel.Customer"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="DataModel.Room"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/header.jspf" %>
<%
    Map<String, Long> countAvail = (Map<String, Long>)request.getAttribute("count_avail");
    Map<String, Double> roomRate = (Map<String, Double>)request.getAttribute("room_rates");
    Map<String, Integer> roomsReq = (Map<String, Integer>)request.getAttribute("rooms_requested");
    List<String> messages = (List<String>)request.getAttribute("messages");
    Booking b = (Booking)request.getAttribute("booking");
    boolean permitCancel = b.getRooms().get(0).getCheckin().isAfter(LocalDate.now());
    DecimalFormat df = new DecimalFormat("###,##0.00");
    boolean showQuant = !Objects.isNull(countAvail);
%>
<script src="js/bookingroom.js"></script>
<div class="main-content">
    <h2>Update Your Booking</h2>
    <div class="flexCont">
        <%-- Print out errors --%>
        <%
            for(String m : messages) {
                String[] parts = m.split("#");
                String cssClass = "message-" + parts[0];
        %>
        <div class="flexItem <% out.print(cssClass); %>" onclick="removeMessage(this)">
            <% out.print(parts[1]); %>
        </div>
        <%
            }
        %>
    </div>
    <form method="POST">
        <fieldset class="content-bg">
            <legend>Rooms</legend>
            <div class="form-card-container" id="room_messages">
                <div class="form-card">
                    Standard Double Room
                    <img src="images/std_d.jpeg" class="room-thumb" alt="Standard Double Room">
                    <div class="form-spacing">£<span data-roomtype="std_d" data-roomprice="<% out.print(df.format(roomRate.get("std_d"))); %>"><% out.print(df.format(roomRate.get("std_d"))); %></span> per night</div>
                    <label for="std_d" class="form-spacing-small">Quantity</label>
                    <input id="std_d" class="form-spacing" type="number" name="std_d" min="0" value="<% out.print(roomsReq.get("std_d")); %>" onchange="hide_payment(); calc_price();">
                    <%-- This section should only be displayed if a query was submitted --%>
                    <% if (showQuant) { %>
                    <div>
                        <div class="form-spacing-small">Available</div>
                        <div class="count_avail"><% out.print((countAvail.get("std_d") == null ? 0 : countAvail.get("std_d"))); %></div>
                    </div>
                    <% } %>
                </div>
                <div class="form-card">
                    Standard Twin Room
                    <img src="images/std_t.jpg" class="room-thumb" alt="Standard Twin Room">
                    <div class="form-spacing">£<span data-roomtype="std_t" data-roomprice="<% out.print(df.format(roomRate.get("std_t"))); %>"><% out.print(df.format(roomRate.get("std_t"))); %></span> per night</div>
                    <label for="std_t" class="form-spacing-small">Quantity</label>
                    <input id="std_t" class="form-spacing" type="number" name="std_t" min="0" value="<% out.print(roomsReq.get("std_t")); %>" onchange="hide_payment(); calc_price();">
                    <%-- This section should only be displayed if a query was submitted --%>
                    <% if (showQuant) { %>
                    <div>
                        <div class="form-spacing-small">Available</div>
                        <div class="count_avail"><% out.print((countAvail.get("std_t") == null ? 0 : countAvail.get("std_t"))); %></div>
                    </div>
                    <% } %>
                </div>
                <div class="form-card">
                    Superior Double Room
                    <img src="images/sup_d.jpg" class="room-thumb" alt="Superior Double Room">
                    <div class="form-spacing">£<span data-roomtype="sup_d" data-roomprice="<% out.print(df.format(roomRate.get("sup_d"))); %>"><% out.print(df.format(roomRate.get("sup_d"))); %></span> per night</div>
                    <label for="sup_d" class="form-spacing-small">Quantity</label>
                    <input id="sup_d" class="form-spacing" type="number" name="sup_d" min="0" value="<% out.print(roomsReq.get("sup_d")); %>" onchange="hide_payment(); calc_price();">
                    <%-- This section should only be displayed if a query was submitted --%>
                    <% if (showQuant) { %>
                    <div>
                        <div class="form-spacing-small">Available</div>
                        <div class="count_avail"><% out.print((countAvail.get("sup_d") == null ? 0 : countAvail.get("sup_d"))); %></div>
                    </div>
                    <% } %>                
                </div>
                <div class="form-card">
                    Superior Twin Room
                    <img src="images/sup_t.jpg" class="room-thumb" alt="Superior Twin Room">
                    <div class="form-spacing">£<span data-roomtype="sup_t" data-roomprice="<% out.print(df.format(roomRate.get("sup_t"))); %>"><% out.print(df.format(roomRate.get("sup_t"))); %></span> per night</div>
                    <label for="sup_t" class="form-spacing-small">Quantity</label>
                    <input id="sup_t" class="form-spacing" type="number" name="sup_t" min="0" value="<% out.print(roomsReq.get("sup_t")); %>" onchange="hide_payment(); calc_price();">
                    <%-- This section should only be displayed if a query was submitted --%>
                    <% if (showQuant) { %>
                    <div>
                        <div class="form-spacing-small">Available</div>
                        <div class="count_avail"><% out.print((countAvail.get("sup_t") == null ? 0 : countAvail.get("sup_t"))); %></div>
                    </div>
                    <% } %>
                </div>
                <div class="form-card">
                    <div class="form-spacing-small">Price</div> 
                    <div class="text-large">£<span id="cost_span">0.00</span></div>
                    <input type="hidden" name="b_ref" id="b_red" value="<% out.print(b.getRef()); %>">
                    <%--Hide customer ID--%>
                    <div class="form-spacing-small">Check-in Date</div>
                    <input class="form-spacing" type="date" name="checkin" value="<% out.print(b.getRooms().get(0).getCheckin()); %>" id="rooms_check_in" onchange="sanity_dates(this); hide_payment(); calc_price();" required>
                    <div class="form-spacing-small">Check-out Date</div>
                    <input class="form-spacing" type="date" name="checkout" value="<% out.print(b.getRooms().get(0).getCheckout()); %>" id="rooms_check_out" onchange="sanity_dates(this); hide_payment(); calc_price();" required>
                </div>
            </div>
        </fieldset>
    <span id="pay_open"></span>
    <%-- This section will only print when okay to go ahead and make booking --%>
        <fieldset class="content-bg">
            <legend>Payment Details & Notes</legend>
            <div class="form-card-container">
                <div class="form-card">
                    <input type="hidden" name="c_id"  value="<% out.print(b.getCustomer().getNo()); %>"  >
                    <div class="form-spacing"><% out.print(b.getCustomer().getName()); %></div>
                    <div class="form-spacing-small">Email Address</div>
                    <input type="email" name="email" id="email" placeholder="Email Address" class="form-spacing" value="<% out.print(b.getCustomer().getEmail()); %>" required>
                    <label for="address" class="form-spacing-small">Address</label>
                    <textarea id="address" name="address" required><% out.print(b.getCustomer().getAddress()); %></textarea>
                     <label for="notes" class="form-spacing-small">Notes</label>
                    <textarea id="notes" name="notes"><% out.print(b.getNotes()); %></textarea>
                </div>
                <div class="form-card">
                    <div class="form-spacing-small">Card Number</div>
                    <input type="text" name="cardno" id="cardno" pattern="[0-9]{15,19}" placeholder="Card Number, without spaces" class="form-spacing" value="<% out.print(b.getCustomer().getCardno()); %>" required>
                    <div class="form-spacing-small">Card Expiry</div>
                    <input type="text" name="cardexp" id="cardexp" placeholder="Card Expiry (MM/YY)" pattern="\d{2}\/\d{2}" class="form-spacing" value="<% out.print(b.getCustomer().getCardexp()); %>" required> 
                    <div class="form-spacing-small">Card Type</div>
                    <label for="address" class="form-spacing-small"></label>
                    <select name="cardtype" id="cardtype" class="form-spacing" required>
                        <option value="" disabled>Select Card Type</option>
                        <option value="V" <% if (b.getCustomer().getCardtype().equals("V")) { out.print(" selected"); } %>>Visa</option>
                        <option value="MC"<% if (b.getCustomer().getCardtype().equals("MC")){ out.print(" selected"); } %>>Mastercard</option>
                        <option value="A"<% if (b.getCustomer().getCardtype().equals("A")) { out.print(" selected"); } %>>American Express</option>
                    </select>
                </div>
            </div>
        </fieldset>
        <fieldset class="content-bg">
            <legend>Update Booking</legend>
            <div class="form-card-container">
                <div class="form-card">
                    <button type="submit" name="btn_update" class="button">Update Booking</button>
                </div>
                <% if(permitCancel) { %>
                <div class="form-card">
                    <button type="submit" name="btn_cancel" value="cancel" class="button message-error">Cancel Booking</button>
                </div>
                <% } %>
            </div>
        </fieldset>
    </form>
</div>
<%@include file="/WEB-INF/footer.jspf" %>