<%-- 
    Document   : home
    Created on : 29-Nov-2017, 12:20:38
    Author     : x3041557
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/header.jspf" %>
    <div class="slideshow">
      <img class="slide" src="images/bar.jpeg" alt="Hotel Bar">
      <img class="slide" src="images/breakfast.jpeg" alt="Breakfast">
      <img class="slide" src="images/bedroom.jpeg" alt="Sample Bedroom">
      <img class="slide" src="images/beach.jpeg" alt="Local Beach">
    </div>
    <button class="slide-button dropshadow-bottom" onclick="plusDivs(-1)">&#10094;</button>
    <button class="slide-button dropshadow-bottom" onclick="plusDivs(1)">&#10095;</button>
    <div class="main-content">
        <h2>Welcome</h2>
        <div class="content">Blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah </div>
        <div class="content">This is an amazing website blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah </div>
        <script src="js/slideshow.js"></script>
    </div>
<%@include file="/WEB-INF/footer.jspf" %>