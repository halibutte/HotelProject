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
    
   String roomNo_msg = (String)request.getAttribute("roomNo_msg");
   String dates_message = (String)request.getAttribute("dates_message");
   String selection_message = (String)request.getAttribute("selection_message");
   String proceed_message = (String)request.getAttribute("proceed_message");
%>
<script src="js/bookingroom.js"></script>
<div class="main-content">
    <h2>Booking</h2>
    <div class="flexCont" id="message_container"> </div>
    <form method="GET">
        <fieldset class="content-bg">
            <legend>Rooms</legend>
            <div class="form-card-container" id="room_messages">
                <div class="form-card">
                    <div class="form-spacing-small">Customer ID</div> 
                    <input class="form-spacing" type="number" name="c_id" id="c_id" size="10" value="<% out.print(((request.getAttribute("CustID") == null ? "" : request.getAttribute("CustID")))); %>" placeholder="Can be blank" onchange="hide_payment()">
                    <%--Hide customer ID--%>
                    <div class="form-spacing-small">Check-in Date</div>
                    <input class="form-spacing" type="date" name="c_in_date" value="<% out.print(request.getAttribute("Checkin")); %>" id="rooms_check_in" onchange="sanity_dates(this); hide_payment(); calc_price();" required>
                    <div class="form-spacing-small">Check-out Date</div>
                    <input class="form-spacing" type="date" name="c_out_date" value="<% out.print(request.getAttribute("Checkout")); %>" id="rooms_check_out" onchange="sanity_dates(this); hide_payment(); calc_price();" required>
                </div>
                <div class="form-card">
                    Standard Double Room
                    <img src="images/std_d.jpeg" class="room-thumb" alt="Standard Double Room">
                    <div class="form-spacing">£<span data-roomtype="std_d" data-roomprice="<% out.print(df.format(roomRate.get("std_d"))); %>"><% out.print(df.format(roomRate.get("std_d"))); %></span> per night</div>
                    <label for="std_d" class="form-spacing-small">Quantity</label>
                    <input id="std_d" class="form-spacing" type="number" name="std_d" min="0" value="<% out.print(request.getAttribute("StdDno")); %>" onchange="hide_payment(); calc_price();">
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
                    <input id="std_t" class="form-spacing" type="number" name="std_t" min="0" value="<% out.print(request.getAttribute("StdTno")); %>" onchange="hide_payment(); calc_price();">
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
                    <input id="sup_d" class="form-spacing" type="number" name="sup_d" min="0" value="<% out.print(request.getAttribute("SupDno")); %>" onchange="hide_payment(); calc_price();">
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
                    <input id="sup_t" class="form-spacing" type="number" name="sup_t" min="0" value="<% out.print(request.getAttribute("SupTno")); %>" onchange="hide_payment(); calc_price();">
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
                    <div class="text-large form-spacing">£<span id="cost_span">0.00</span></div>
                    <button type="submit" class="button">Search Rooms</button>
                </div>
            </div>
        </fieldset>
    </form>
    <div class="flexCont" id="message_confirm"> </div>
    <%if (request.getAttribute("b_message").equals(roomNo_msg)) {
            messages.add(request.getAttribute("b_message").toString());
        } else if (!request.getAttribute("b_message").equals(roomNo_msg)) {
            if (request.getAttribute("d_message").equals(dates_message)) {
                messages.add(request.getAttribute("d_message").toString());
            }
            if (!request.getAttribute("d_message").equals(dates_message)) {
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
                if (request.getAttribute("s_message").equals(selection_message)) {
                    messages.add(request.getAttribute("s_message").toString());
                }
                if (request.getAttribute("s_message").equals(proceed_message)) {
                    messages.add(rooms_msg = request.getAttribute("s_message").toString());
    %>
    <span id="pay_open"></span>
    <%-- This section will only print when okay to go ahead and make booking --%>
    <form method="POST" id="payment_form">
        <fieldset class="content-bg">
            <legend id="pay_anchor">Payment Details and notes</legend>
            <div class="form-card-container">
                <div class="form-card">
                    <input type="hidden" name="c_id"  value="<% out.print(request.getAttribute("CustID")); %>"  >
                    <input type="text" name="name" id="name" placeholder="Name" class="form-spacing" value="<% out.print(request.getAttribute("Name")); %>" required>
                    <input type="email" name="email" id="email" placeholder="Email Address" class="form-spacing" value="<% out.print(request.getAttribute("Email")); %>" required>
                    <label for="address" class="form-spacing-small">Address</label>
                    <textarea id="address" name="address" required><% out.print(request.getAttribute("Address")); %></textarea>
                    <label for="notes" class="form-spacing-small">Notes</label>
                    <textarea id="notes" name="notes"><% out.print(request.getAttribute("Notes")); %></textarea>
                </div>
                <div class="form-card">
                    <input type="text" name="cardno" id="card_no" pattern="[0-9]{15,19}"  placeholder="Card Number, without spaces" class="form-spacing" value="<% out.print(request.getAttribute("Cardno")); %>" required>
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