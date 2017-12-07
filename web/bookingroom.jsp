<%-- 
    Document   : Booking
    Created on : 28-Nov-2017, 16:30:52
    Author     : gmc13udu
--%>


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
    String rooms_msg = "";
    Map<String, Long> countAvail = (Map<String, Long>) request.getAttribute("count_avail");
    Map<String, Double> roomRate = (Map<String, Double>) request.getAttribute("room_rates");
    List<String> messages = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("###,##0.00");
    boolean showQuant = !Objects.isNull(countAvail);
%>
<script src="js/bookingroom.js"></script>
<div class="main-content">
    <h2>Booking</h2>
    <form method="GET">
        <fieldset class="content-bg">
            <legend>Rooms</legend>
            <div class="form-card-container" id="room_messages">
                <div class="form-card">
                    Standard Double Room
                    <img src="images/std_d.jpeg" class="room-thumb" alt="Standard Double Room">
                    <div class="form-spacing">£<% out.print(df.format(roomRate.get("std_d"))); %> per night</div>
                    <label for="std_d" class="form-spacing-small">Quantity</label>
                    <input id="std_d" class="form-spacing" type="number" name="std_d" min="0" value="<% out.print(request.getAttribute("StdDno")); %>" onchange="hide_payment()">
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
                    <div class="form-spacing">£<% out.print(df.format(roomRate.get("std_t"))); %> per night</div>
                    <label for="std_t" class="form-spacing-small">Quantity</label>
                    <input id="std_t" class="form-spacing" type="number" name="std_t" min="0" value="<% out.print(request.getAttribute("StdTno")); %>" onchange="hide_payment()">
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
                    <div class="form-spacing">£<% out.print(df.format(roomRate.get("sup_d"))); %> per night</div>
                    <label for="sup_d" class="form-spacing-small">Quantity</label>
                    <input id="sup_d" class="form-spacing" type="number" name="sup_d" min="0" value="<% out.print(request.getAttribute("SupDno")); %>" onchange="hide_payment()">
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
                    <div class="form-spacing">£<% out.print(df.format(roomRate.get("sup_t"))); %> per night</div>
                    <label for="sup_t" class="form-spacing-small">Quantity</label>
                    <input id="sup_t" class="form-spacing" type="number" name="sup_t" min="0" value="<% out.print(request.getAttribute("SupTno")); %>" onchange="hide_payment()">
                    <%-- This section should only be displayed if a query was submitted --%>
                    <% if (showQuant) { %>
                    <div>
                        <div class="form-spacing-small">Available</div>
                        <div class="count_avail"><% out.print((countAvail.get("sup_t") == null ? 0 : countAvail.get("sup_t"))); %></div>
                    </div>
                    <% } %>
                </div>
                <div class="form-card">
                    <div class="form-spacing-small">Customer ID</div> 
                    <input class="form-spacing" type="number" name="c_id" id="c_id" size="10" value="<% out.print(((request.getAttribute("CustID") == null ? "" : request.getAttribute("CustID")))); %>" placeholder="Can be blank" onchange="hide_payment()">
                    <%--Hide customer ID--%>
                    <div class="form-spacing-small">Check-in Date</div>
                    <input class="form-spacing" type="date" name="c_in_date" value="<% out.print(request.getAttribute("Checkin")); %>" id="rooms_check_in" onchange="sanity_dates(this); hide_payment()" required>
                    <div class="form-spacing-small">Check-out Date</div>
                    <input class="form-spacing" type="date" name="c_out_date" value="<% out.print(request.getAttribute("Checkout")); %>" id="rooms_check_out" onchange="sanity_dates(this); hide_payment()" required>
                    <button type="submit" class="button">Search Rooms</button>
                </div>
            </div>
        </fieldset>
    </form>
    <%if (request.getAttribute("b_message").equals("Enter number of rooms")) {
            messages.add(request.getAttribute("b_message").toString());
        } else if (!request.getAttribute("b_message").equals("Enter number of rooms")) {
            if (request.getAttribute("d_message").equals("Please enter/check dates")) {
                messages.add(request.getAttribute("d_message").toString());
            }
            if (!request.getAttribute("d_message").equals("Please enter/check dates")) {
                if (!request.getAttribute("stdt_message").equals("")) {
                    messages.add(request.getAttribute("stdt_message").toString());
                }
                if (!request.getAttribute("stdd_message").equals("")) {
                    messages.add(request.getAttribute("stdd_message").toString());
                }
                if (!request.getAttribute("supt_message").equals("")) {
                    messages.add(request.getAttribute("supt_message").toString());
                }
                if (!request.getAttribute("supd_message").equals("")) {
                    messages.add(request.getAttribute("supd_message").toString());
                }
                if (request.getAttribute("s_message").equals("Please change your selection")) {
                    messages.add(request.getAttribute("s_message").toString());
                }
                if (request.getAttribute("s_message").equals("Preference available. You can proceed to enter your details and make a booking or serach for other preferences")) {
                    messages.add(rooms_msg = request.getAttribute("s_message").toString());
    %>
    <span id="pay_open"></span>
    <%-- This section will only print when okay to go ahead and make booking --%>
    <form method="POST" id="payment_form">
        <fieldset class="content-bg">
            <legend id="pay_anchor">Payment Details</legend>
            <div class="form-card-container">
                <div class="form-card">
                    <input type="hidden" name="c_id"  value="<% out.print(request.getAttribute("CustID")); %>"  >
                    <input type="text" name="name" id="name" placeholder="Name" class="form-spacing" value="<% out.print(request.getAttribute("Name")); %>" required>
                    <input type="email" name="email" id="email" placeholder="Email Address" class="form-spacing" value="<% out.print(request.getAttribute("Email")); %>" required>
                    <label for="address" class="form-spacing-small">Address</label>
                    <textarea id="address" name="address" required><% out.print(request.getAttribute("Address")); %></textarea>
                </div>
                <div class="form-card">
                    <input type="number" name="cardno" id="card_no" min="999999999999999" max="9999999999999999" placeholder="Card Number, without spaces" class="form-spacing" value="<% out.print(request.getAttribute("Cardno")); %>" required>
                    <input type="text" name="cardexp" id="card_exp" placeholder="Card Expiry (MM/YY)" pattern="\d{2}\/\d{2}" class="form-spacing" value="<% out.print(request.getAttribute("Cardexp")); %>" required> 
                    <% if(request.getAttribute("c_message").equals("Enter valid card expiry")){out.print("<div class=\"message-error form-spacing card-nowidth\">" + request.getAttribute("c_message") + "</div>");}%>
                    <label for="address" class="form-spacing-small"></label>
                    <select name="cardtype" id="card_type" class="form-spacing" required>
                        <option value="" disabled <% if (request.getAttribute("Cardtype").equals("")) { out.print(" selected"); } %>>Select Card Type</option>
                        <option value="V" <% if (request.getAttribute("Cardtype").equals("V")) { out.print(" selected"); } %>>Visa</option>
                        <option value="MC"<% if (request.getAttribute("Cardtype").equals("MC")){ out.print(" selected"); } %>>Mastercard</option>
                        <option value="A"<% if (request.getAttribute("Cardtype").equals("A")) { out.print(" selected"); } %>>American Express</option>
                    </select>
                    <button type="submit" name="bButton" class="button">Make Booking</button>
                </div>  

            </div>
        </fieldset>
    </form>
    <%}
    }
    } %>
    <div class="message-error message-relocate flexItem" onclick="removeMessage(this)">
        <% for(String message : messages) {
            out.print("<div class='form-spacing-small'>");
            out.print(message+"</div>");
        }
        %>
    </div>
</div>
<%@include file="/WEB-INF/footer.jspf" %>