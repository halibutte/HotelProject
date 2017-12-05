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
        <div class="content">
            <img src="images/hotel-small.jpg" class="float-left" alt="The hotel and grounds">
            <!-- Largely sourced from http://www.hotelhesperia.com/description-and-services/ -->
            <p>The Heartache Hotel is the right choice for visitors who are searching for a combination of charm, peace and quiet, and a convenient position from which to explore Norfolk.</p> 
            <p>It is a small, comfortable hotel, situated on the ringroad. The family and their staff offer an attentive, personalized service and are always available to offer any help to guests.</p>
            <p>The hotel is arranged on three floors, without a lift. On the ground floor, apart from the reception, there is a comfortable lounge where you can sit and drink tea, or just read. There is also a splendid terrace, where, you can relax and immerse yourself from morning onwards in the atmosphere of Norfolk country life, watching people travelling about the broads and cows grazing.</p>
            <p>The rooms are arranged on the first, second and third floors.</p>
            <p>The buffet breakfast is served in the lounge on the ground floor, and also outside on our little patio during the summer months.</p>
        </div>
        <script src="js/slideshow.js"></script>
    </div>
<%@include file="/WEB-INF/footer.jspf" %>