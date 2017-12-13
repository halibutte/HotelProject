<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/header.jspf" %>
<!--Pull Slideshow Java Script-->
    <script src="./js/slideshow.js"></script> 
    <div class="slideshow">
      <img class="slide" src="images/bar.jpeg" alt="Hotel Bar">
      <img class="slide" src="images/breakfast.jpeg" alt="Breakfast">
      <img class="slide" src="images/bedroom.jpeg" alt="Sample Bedroom">
      <img class="slide" src="images/beach.jpeg" alt="Local Beach">
    </div>
    <div>
    <button class="slide-button dropshadow-bottom" onclick="plusDivs(-1)">&#10094;</button>
    <button class="slide-button dropshadow-bottom" onclick="plusDivs(1)">&#10095;</button>
    </div>
    <div class="main-content">
        <br><h1>Information</h1>
        <fieldset class="content-bg">
            <!--Forms cards used for mobile compatability-->
            <legend>Rooms</legend>
            <div class="form-card-container center-flex" id="room_messages">
                <div class="form-card">
                    Standard Double Room
                    <img src="images/std_d.jpeg" class="room-thumb" alt="Standard Double Room">
                    <div class="form-spacing">£65.00 per night</div>
                    <div class="form-spacing-small">Details</div>
                    <ul class="room-desc">
                        <li>Pocket sprung full sized double bed</li>
                        <li>28" Flat screen TV</li>
                        <li>Writing desk with electrical sockets</li>
                        <li>Office chair</li>
                        <li>Tea Services</li>
                        <li>Ensuite bathroom with shower</li>
                    </ul>
                </div>
                <div class="form-card">
                    Standard Twin Room
                    <img src="images/std_t.jpg" class="room-thumb" alt="Standard Twin Room">
                    <div class="form-spacing">£62.00 per night</div>
                    <div class="form-spacing-small">Details</div>
                    <ul class="room-desc">
                        <li>2X Pocket sprung full sized single bed</li>
                        <li>28" Flat screen with DVD player</li>
                        <li>Writing desk with electrical sockets</li>
                        <li>Office chair</li>
                        <li>Tea Services</li>
                        <li>Ensuite bathroom with shower</li>
                    </ul>
                </div>
                <div class="form-card">
                    Superior Double Room
                    <img src="images/sup_d.jpg" class="room-thumb" alt="Superior Double Room">
                    <div class="form-spacing">£77.00 per night</div>
                    <div class="form-spacing-small">Details</div>
                    <ul class="room-desc">
                        <li>38" Flat screen TV & Blu ray player</li>
                        <li>Luxury King sized Oak bed with Memory Foam mattress</li>
                        <li>Luxury Writing Desk</li>
                        <li>Tea & Coffee maker</li>
                        <li>Office chair</li>
                        <li>Ensuite bathroom with shower and bath facilities</li>
                    </ul>
                </div>
                <div class="form-card">
                    Superior Twin Room
                    <img src="images/sup_t.jpg" class="room-thumb" alt="Superior Twin Room">
                    <div class="form-spacing">£75.00 per night</div>
                    <div class="form-spacing-small">Details</div>
                    <ul class="room-desc">
                        <li>38" Flat screen TV & Blu ray player</li>
                        <li>2X Luxury Queen sized Oak bed with Memory Foam mattress</li>
                        <li>Luxury Writing Desk</li>
                        <li>Tea & Coffee maker</li>
                        <li>Office chair</li>
                        <li>Ensuite bathroom with shower and bath facilities</li>
                    </ul>
                </div>
            </div>
        </fieldset>
        <div class="content">
            
            <img id="infopic" src="images/info.jpg" alt="Hotel Info" class="float-right">   
            
            <h2>Times</h2>
            <p>
            Once your booking is complete you will able to check in on the day of your arrival at 12:00. Checkout times must be before 14:00 on the day. <br>
            Using the key that you will be provided with will allow you to enter and exit the hotel freely, please be considerate of other residents between unreasonable hours.<br><br>
            </p>
            
            <h2>Self Catering Services</h2>
            <p>
            Each room has access to drink making services, fresh tea and coffee with a variety of sweeteners, sugars and milk is provided free of charge (If you require more of these, please ask at the reception). <br><br> 
            </p>
            
            <h2>Entertainment</h2>
            <p>
            Each room has provides a TV and DVD player, extra ports are available incase you wish to connect a device to the TV, Cables can be requested at the reception. The hotel bar is open from 06:00-10:00 and again at 12:00-23:00 each day of the week and provides a variety of refreshments. <br><br>
            </p>
            
            <h2>Provided Services</h2>
            <div>
            Your stay will come with a variety of freely provided services.<br><br> Each room is fully serviced with:
            <ul>
                <li> Towels </li>
                <li> Hair dryer</li>
                <li> Alarm </li>
                <li> Safe (Code can be requested from the Reception) </li>
                <li> Body wash and shampoo's </li>
                <li> Wardrobe with hangers</li>
            </ul>
            Resident are welcome to bring their own towels and toiletries.
            </div>
            
            <h2>WiFi</h2>
            <p>
            Stay connected, our WiFi is available throughout the hotel and its grounds allowing you to stay in touch with the world, whether it's to search the web, relax with a movie or to get some work done.
            <br> Getting connected is as easy as 1-2-3, search for wireless connections using a laptop or mobile device* and select "HeartAche WiFi" and follow the onscreen instructions. If you have trouble connecting your device to the WiFi, please contact the reception using the room extention provided.<br><br>
            *Subject to the device compatability<br><br>
            </p>
        
            <h2>Dogs</h2>
            <p>
            Dogs are more then welcome here at The Heartache Hotel, if you require any support please contact the reception.
            </p>
        </div>    
    </div>
<%@include file="/WEB-INF/footer.jspf" %>
