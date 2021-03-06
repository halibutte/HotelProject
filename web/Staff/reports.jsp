<%-- 
    Document   : reports
    Created on : 04-Dec-2017, 16:43:57
    Author     : x3041557
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.time.LocalDate"%>
<%@page import="DataModel.Report"%>
<%@page import="java.util.Objects"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width">
        <title>Weekly Reports</title>
        <link rel="stylesheet" type="text/css" href="../Main.css">
        <script src="js/jquery.min.js"></script>
        <script src="js/reports.js"></script>
    </head>
    <body>
        <%@include file="staff_header.jsp" %>
        <%
            //get the report list
            List<Report> report = (List<Report>)request.getAttribute("report_list");
            DecimalFormat df = new DecimalFormat("###,##0.00");
        %>
        <div class="main-container">
            
            <div class="col-minwidth">
                <div class="flexCont center-flex">
                    <div class="flexItem">
                        <h4 class="staff">View Dates</h4>
                        <form method="GET">
                            <div class="form-spacing-small">From</div>
                            <input type="date" name="start_date" value="<% out.print(request.getAttribute("start_date")); %>" class="form-spacing">
                            <div class="form-spacing-small">To</div>
                            <input type="date" name="end_date" value="<% out.print(request.getAttribute("end_date")); %>" class="form-spacing">
                            <button type="submit" class="button">Submit</button>
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
                <h3 class="staff">Weekly Reports</h3>
                <% 
                LocalDate thisDate = null;
                LocalDate lastDate = null;
                for(Report r : report) { 
                    thisDate = r.getStartDate();
                    if(!thisDate.equals(lastDate)) { 
                        if(!Objects.isNull(lastDate)) { out.print("</div></div>"); } %>   
                <div class="card">
                <h4 class="staff">Week Commencing <% out.print(thisDate); %></h4>  
                
                <div class="table table-total form-spacing">
                    <div class="table-head">
                        <div class="table-row">
                            <div class="head-cell width-20">
                            </div>
                            <div class="head-cell width-20">
                                Room Income
                            </div>
                            <div class="head-cell width-20">
                                Occupancy %
                            </div>
                            <div class="head-cell width-20">
                                Nights Occupied
                            </div>
                            <div class="head-cell width-20">
                                Extra Income
                            </div>
                        </div>
                    </div>   
                    <div class="table-row">
                        <div class="head-cell">
                            Total
                        </div>
                        <div class="table-cell" data-sum="1">
                        </div>
                        <div class="table-cell" data-avg="3,4">
                        </div>
                        <div class="table-cell" data-sum="3">
                        </div>
                        <div class="table-cell">
                            <% out.print(df.format(r.getExtraSpend())); %>
                        </div>
                    </div>  
                </div>
                
                <div class="table table-details" data-name="room_breakdown">
                    <div class="table-head">
                        <div class="table-row">
                            <div class="head-cell width-20">
                                Room Type
                            </div>
                            <div class="head-cell width-20">
                                Income
                            </div>
                            <div class="head-cell width-20">
                                Occupancy %
                            </div>
                            <div class="head-cell width-20">
                                Nights Occupied
                            </div>
                            <div class="head-cell width-20">
                                Nights Available
                            </div>
                        </div>
                    </div>   
                    <% } %>
                    <div class="table-row">
                        <div class="table-cell">
                            <% out.println(r.getRoomClassFull()); %>
                        </div>
                        <div class="table-cell">
                            <% out.println(df.format(r.getIncome())); %>
                        </div>
                        <div class="table-cell">
                            <% out.println(r.getPercentOccupancy()); %>
                        </div>
                        <div class="table-cell">
                            <% out.println(r.getNightsOccupied()); %>
                        </div>
                        <div class="table-cell">
                            <% out.println(r.getNightsAvail()); %>
                        </div>
                    </div>
                    <% lastDate = thisDate;
                    } %>
                </div>
                </div>
            </div>
        </div>
    </body>
</html>
