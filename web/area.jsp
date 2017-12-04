<%-- 
    Document   : area
    Created on : 29-Nov-2017, 21:39:39
    Author     : Hal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/header.jspf" %>
    <div class="slideshow">
      <img class="slide" src="images/area_broads.jpg" alt="Norfolk Broads">
      <img class="slide" src="images/area_cathedral.jpg" alt="Anglican Cathedral">
      <img class="slide" src="images/area_scva.jpg" alt="Sainsbury Centre for Visual Arts">
      <img class="slide" src="images/area_uea.jpg" alt="Ziggurats at UEA">
    </div>
    <button class="slide-button dropshadow-bottom" onclick="plusDivs(-1)">&#10094;</button>
    <button class="slide-button dropshadow-bottom" onclick="plusDivs(1)">&#10095;</button>
    <div class="main-content">
        <h2>Broads</h2>
        <div class="content">Info about Broads</div>
        <h2>Cathedrals</h2>
        <div class="content">Info about Cathedrals</div>
        <h2>Sainsbury Centre for Visual Arts</h2>
        <div class="content">Info about Sainsbury Centre</div>
        <h2>Ziggurats</h2>
        <div class="content">Info about some lovely brutalist buildings</div>
        
        <script src="js/slideshow.js"></script>
    </div>
<%@include file="/WEB-INF/footer.jspf" %>
