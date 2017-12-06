<%-- 
    Document   : billing
    Created on : 05-Dec-2017, 14:17:27
    Author     : x3041557
--%>

<%@page import="DataModel.BillableItem"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="DataModel.RoomBooking"%>
<%@page import="DataModel.BilledItem"%>
<%@page import="DataModel.Customer"%>
<%@page import="java.util.Objects"%>
<%@page import="DataModel.Booking"%>
<%@page import="DataModel.Report"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width">
        <title>Billing</title>
        <link rel="stylesheet" type="text/css" href="../Main.css">
        <script src="js/jquery.min.js"></script>
        <script src="js/billing.js"></script>
        <script src="js/checkin.js"></script>
    </head>
    <body>
        <%@include file="staff_header.jsp" %>
        <%
            //get the report list
            List<Booking> bookings = (List<Booking>)request.getAttribute("bookings");
            List<BillableItem> items = (List<BillableItem>)request.getAttribute("items");
            DecimalFormat df = new DecimalFormat("###,##0.00");
        %>
        <div class="main-container">
            
            <div class="col-minwidth">
                <div class="flexCont">
                    <div class="flexItem">
                        <h4 class="staff">View Dates</h4>
                        <form method="GET">
                            <input type="date" name="view_date" value="<% out.print(request.getAttribute("view_date")); %>" class="form-spacing">
                            <button type="submit" class="button">Submit</button>
                        </form>
                    </div>
                    <div class="flexItem">
                        <h4 class="staff">Search Guests</h4>
                        <form method="GET">
                            <input id="search_val" type="text" name="search_guests" value="" class="form-spacing">
                        </form>
                    </div>
                    <% List<String> msgs = (List<String>)request.getAttribute("messages");
                    if(!Objects.isNull(msgs)) {
                    for(String s : msgs) {
                        String[] arr = s.split("#");
                    %>
                    <div class="flexItem message-<% out.print(arr[0]); %>" onclick="this.parentNode.removeChild(this)">
                        <% out.print(arr[1]); %>
                    </div>
                    <% }
                    }%>   
                </div>
            </div>
            
                
            <div class="col-full">
                <h3 class="staff">Guests</h3>
                <div class="flexCont">
                <% for(Booking b : bookings) { 
                Customer c = b.getCustomer();
                List<BilledItem> billItems = b.getBilledItems();
                List<RoomBooking> rooms = b.getRooms();
                %>
                <div class="flexItemLarge">
                    <h3 class="staff"><span data-searchon="<% out.print(c.getName()); %>"><% out.print(c.getName()); %></span>
                        <% for(int i = 0; i < rooms.size(); i++) {
                            RoomBooking r = rooms.get(i);
                            String end = ",&nbsp;";
                            if(i == 0) {
                                end = "";
                            }
                            out.print("<span class='payment-expand' data-searchon='" + r.getRoomNo() + "'>" + r.getRoomNo() + end + "</span>");
                        } %>
                    </h3>
                    <div class="table table-total form-spacing">
                        <div class="table-head">
                                <div class="table-row">
                                <div class="head-cell width-35">Name</div>
                                <div class="head-cell width-35">Description</div>
                                <div class="head-cell width-15">Price</div>
                                <div class="head-cell width-15"></div>
                            </div>
                        </div>
                        <% for(BilledItem bitem : billItems) { %>
                        <div class="table-row">
                            <div class="table-cell"><% out.print(bitem.getItem().getName()); %></div>
                            <div class="table-cell"><% out.print(bitem.getDescription()); %></div>
                            <div class="table-cell"><% out.print(df.format(bitem.getPrice())); %></div>
                            <div class="table-cell">
                                <form method="POST">
                                    <button type="submit" class="button-narrow">Remove</button>
                                    <input type="hidden" value="remove" name="act_type">
                                    <input type="hidden" value="<% out.print(bitem.getId()); %>" name="remove_item">
                                </form>
                            </div>    
                        </div>
                        <% } %>
                        <form class="table-row" method="POST">
                            <div class="table-cell">
                                <select name="item_code">
                                    <%
                                    for(BillableItem i : items) {
                                    %>
                                    <option value="<% out.print(i.getCode()); %>" data-defprice="<% out.print(i.getPrice()); %>"><% out.print(i.getName()); %></option>
                                    <%
                                    }
                                    %>
                                </select>
                            </div>
                            <div class="table-cell">
                                <input type="text" name="item_desc" maxlength="30">
                            </div>
                            <div class="table-cell">
                                <input type="number" name="item_price" min="0" step="0.01">
                            </div>
                            <div class="table-cell">
                                <input type="hidden" value="add" name="act_type">
                                <input type="hidden" value="<% out.print(b.getRef()); %>" name="item_bref">
                                <button type="submit" class="button-narrow">Add</button>
                            </div>
                        </form>
                    </div>
                        <div>
                        <form method="POST">
                            <div class="table">
                                <div class="table-cell width-50">
                                    <div class="form-spacing-small">Outstanding Balance</div>
                                    <div class="form-spacing">£<% out.print(df.format(b.getOutstanding())); %></div>
                                </div>
                                <div class="table-cell width-50">
                                    <div class="form-spacing-small">Payment Taken</div>
                                    <input type="number" name="card_amnt" value="0.00" class="form-spacing" step="0.01" min="0" max="<% out.print(b.getOutstanding()); %>">
                                </div>
                            </div>
                            <div class="payment-detail-header">
                                <div class="form-spacing"><span class="payment-expand-text">Payment Details</span>&nbsp;<span class="interactive payment-expand" onclick="toggle_payment(this)">[+]</span> </div>
                                <div class="payment-details">
                                    <div class="form-spacing-small">Card Number</div>
                                    <input type="text" name="card_num" value="<% out.print(c.getCardno()); %>" class="form-spacing" pattern="[\d]{8,19}">
                                    <div class="form-spacing-small">Card Type</div>
                                    <select name="card_type" class="form-spacing">
                                        <option value="V"<% if(c.getCardtype().equals("V")) { out.print(" selected"); } %>>Visa</option>
                                        <option value="MC"<% if(c.getCardtype().equals("MC")) { out.print(" selected"); } %>>Mastercard</option>
                                        <option value="A"<% if(c.getCardtype().equals("A")) { out.print(" selected"); } %>>American Express</option>
                                    </select>
                                    <div class="form-spacing-small">Card Expiry</div>
                                    <input type="text" name="card_exp" value="<% out.print(c.getCardexp()); %>" class="form-spacing" pattern="[\d]{2}/\d{2}">
                                </div>
                            </div>
                            <input type="hidden" value="pay" name="act_type">
                            <input type="hidden" value="<% out.print(b.getRef()); %>" name="card_bref">
                            <button type="submit" class="button" name="submit">Take Payment</button>
                        </form>
                    </div>
                </div>
                <% } %>
                </div>
            </div>
    </body>
</html>
