
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/header.jspf" %>
<div class="main-content">
<h2>About Us</h2>
<div class="content">
    
<img id="hotelteam" src="images/about_image.jpg" alt="Hotel team" class="float-right">
<p>The Hotel derives it's name after the previous owner had a heart attack, slipped on a banana, fell out the window and landed on a children's bouncy castle. 
    He then proceeded to vault through the window in the bottom floor ruining a wedding event, the couple were divorced a week later.</p>
<p><u>Contact</u><br> You can get in contact with us by telephone, email, postal and social media!<br><br>
    
    <u>Telephone:</u><br>
    (+44)1603 123456<br><br>
    <u>Email:</u><br>
    Bookings@HeartacheHotel.co.uk<br><br>
    <u>Social Media:</u> <a href="#Bot">Links below</a><br><br> <!--Anchor point A-->
    <u>Address:</u><br>
    Reception Staff<br>
    The Heartache Hotel<br>
    Norwich Research Park<br>
    Norwich<br>
    NR4 7TJ<br>
    
</p>

    
</div>

<div id="googleMap_content" class="content">
<div id="googleMap" class="float-left"></div>
<!--Lat and Lon of the UEA and how zoomed the map is-->            
<script>
//from https://developers.google.com/maps/documentation/javascript/adding-a-google-map
function myMap() {
var mapProp= {
    center:new google.maps.LatLng(52.621975, 1.238962),
    zoom:15,
};
var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
}
</script>
<!--Link to Google Maps with a linked API code received through jdk17aku@gmail.com-->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDj4utM6YO9b-nnjifFeJwXm2J6A7P3HL0&callback=myMap"></script>
        </div>
<div class= "main-content">
    <div class="content">
        <nav class="navbar"><a name="Bot"></a> <!--Anchor point B-->
        <a href="https://www.facebook.com" target="_blank"><img alt="Facebook" id="Facebook" src="images/facebook.png"></a>
        <a href="https://twitter.com/login?lang=en-gb" target="_blank"><img alt="Twitter" id="Twitter" src="images/twitter.png"></a>
        <a href="https://plus.google.com/discover" target="_blank"><img alt="Google" id="Google" src="images/google-plus.png"></a>
        <a href="https://uk.linkedin.com/" target="_blank"><img alt="Linkedin" id="Linked" src="images/linkedin.png"></a>
        <a href="https://www.pinterest.co.uk/" target="_blank"><img alt="Pinterest" id="Pin" src="images/pinterest.png"></a>
        <a href="https://www.youtube.com/" target="_blank"><img alt="Youtube" id="Youtube" src="images/youtube.png"></a>
    </nav>
</div>
</div>
</div>
<%@include file="/WEB-INF/footer.jspf" %>