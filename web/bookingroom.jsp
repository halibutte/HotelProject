<%-- 
    Document   : Booking
    Created on : 28-Nov-2017, 16:30:52
    Author     : gmc13udu
--%>


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
    String payment_msg = "";
    Map<String,Long> countAvail = (Map<String,Long>) request.getAttribute("count_avail");
    Map<String,Double> roomRate = (Map<String,Double>) request.getAttribute("room_rates");
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
                    <label for="checkin" class="form-spacing-small">Quantity</label>
                    <input class="form-spacing" type="number" name="std_d" min="0" value="<% out.print(request.getAttribute("StdDno")); %>">
                    <%-- This section should only be displayed if a query was submitted --%>
                    <% if(showQuant) { %>
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
                    <label for="checkin" class="form-spacing-small">Quantity</label>
                    <input class="form-spacing" type="number" name="std_t" min="0" value="<% out.print(request.getAttribute("StdTno")); %>">
                    <%-- This section should only be displayed if a query was submitted --%>
                    <% if(showQuant) { %>
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
                    <label for="checkin" class="form-spacing-small">Quantity</label>
                    <input class="form-spacing" type="number" name="sup_d" min="0" value="<% out.print(request.getAttribute("SupDno")); %>">
                    <%-- This section should only be displayed if a query was submitted --%>
                    <% if(showQuant) { %>
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
                    <label for="checkin" class="form-spacing-small">Quantity</label>
                    <input class="form-spacing" type="number" name="sup_t" min="0" value="<% out.print(request.getAttribute("SupTno")); %>">
                    <%-- This section should only be displayed if a query was submitted --%>
                    <% if(showQuant) { %>
                    <div>
                        <div class="form-spacing-small">Available</div>
                        <div class="count_avail"><% out.print((countAvail.get("sup_t") == null ? 0 : countAvail.get("sup_t"))); %></div>
                    </div>
                    <% } %>
                </div>
                <div class="form-card">
                    <div class="form-spacing-small">Customer ID</div> 
                    <input class="form-spacing" type="number" name="c_id" size="10" value="<% out.print(request.getAttribute("CustID")); %>" placeholder="Can be blank">
                    <%--Hide customer ID--%>
                    <div class="form-spacing-small">Check-in Date</div>
                    <input class="form-spacing" type="date" name="c_in_date" value="<% out.print(request.getAttribute("Checkin")); %>" placeholder="dd/mm/yyyy"  required>
                    <div class="form-spacing-small">Check-out Date</div>
                    <input class="form-spacing" type="date" name="c_out_date" value="<% out.print(request.getAttribute("Checkout")); %>" placeholder="dd/mm/yyyy" required>
                    <button type="submit" class="button">Submit</button>
                </div>
        </div>
        </fieldset>
        </form>
        <div class="message-error message-relocate flexItem" onclick="removeMessage(this)">
        <%if (request.getAttribute("b_message").equals("Enter number of rooms")) {
                rooms_msg = request.getAttribute("b_message").toString();
                out.print(rooms_msg);
            } else %>
        <%if (!request.getAttribute("b_message").equals("Enter number of rooms")) {%>
        <%if (request.getAttribute("d_message").equals("Please enter/check dates")) {
                rooms_msg = request.getAttribute("d_message").toString();
                out.print(rooms_msg);
            }%>
        <%if (!request.getAttribute("d_message").equals("Please enter/check dates")) {%>
            <% if (!request.getAttribute("stdt_message").equals("")) {
                    rooms_msg = request.getAttribute("stdt_message").toString();
                    out.print(rooms_msg);
                } %>
            <% if (!request.getAttribute("stdd_message").equals("")) {
                    rooms_msg = request.getAttribute("stdd_message").toString();
                    out.print(rooms_msg);
                } %>
            <% if (!request.getAttribute("supt_message").equals("")) {
                    rooms_msg = request.getAttribute("supt_message").toString();
                    out.print(rooms_msg);
                } %>
            <% if (!request.getAttribute("supd_message").equals("")) {
                    rooms_msg = request.getAttribute("supd_message").toString();
                    out.print(rooms_msg);
                } %>
            <% if (request.getAttribute("s_message").equals("Please change your selection")) {
                    rooms_msg = request.getAttribute("s_message").toString();
                    out.print(rooms_msg);
                } %>

            <% if (request.getAttribute("s_message").equals("Preference available. You can proceed to enter your details and make a booking")) {%>

            <% rooms_msg = request.getAttribute("s_message").toString();
            out.print(rooms_msg);
            
            %></div>
            <span id="pay_open"></span>
            
            <%-- This section will only print when okay to go ahead and make booking --%>
            <form method="POST">
                <fieldset class="content-bg">
                    <legend>Payment Details</legend>
                    <div class="form-card-container">
                        <div class="form-card">
                            <input type="hidden" name="c_id"  value="<% out.print(request.getAttribute("CustID")); %>"  >
                            <input type="text" name="name" id="name" placeholder="Name" class="form-spacing" value="<% out.print(request.getAttribute("Name")); %>" required>
                            <input type="text" name="email" id="email" placeholder="Email Address" class="form-spacing" value="<% out.print(request.getAttribute("Email")); %>" required>
                            <label for="address" class="form-spacing-small">Address</label>
                            <textarea id="address" name="address" required><% out.print(request.getAttribute("Address")); %></textarea>
                        </div>
                        <div class="form-card">
                            <input type="text" name="cardno" id="card_no" placeholder="Card Number, without spaces" class="form-spacing" value="<% out.print(request.getAttribute("Cardno")); %>" required>
                            <input type="text" name="cardexp" id="card_exp" placeholder="Card Expiry (MM/YY)" pattern="\d{2}\/\d{2}" class="form-spacing" value="<% out.print(request.getAttribute("Cardexp")); %>" required>
                            <label for="address" class="form-spacing-small" required></label>
                            <select name="cardtype" id="card_type" class="form-spacing" required>
                                <option value="V" <% if(request.getAttribute("Cardtype").equals("V")) { out.print(" selected"); } %>>Visa</option>
                                <option value="MC"<% if(request.getAttribute("Cardtype").equals("MC")) { out.print(" selected"); } %>>Mastercard</option>
                                <option value="A"<% if(request.getAttribute("Cardtype").equals("A")) { out.print(" selected"); } %>>American Express</option>
                            </select>
                            <button type="submit" name="bButton" class="button">Make Booking</button>
                        </div>  

                    </div>
                </fieldset>
            </form>
                    <%}%>
                    <%}%>
                    <%-- <div><% List<Room> r_Available = (List<Room>)request.getAttribute("r_available");
                         for(Room r : r_Available) {
                         %>
                         <%if (request.getAttribute("b_message").equals("The following rooms are available")){ %>Room <% out.print(r.getNo() +" "+ r.getRoomClass()); %>  <br>
                     <%}%> 
                     <%}%>
                     </div> --%>
        <%}%>
</div>
<%@include file="/WEB-INF/footer.jspf" %>