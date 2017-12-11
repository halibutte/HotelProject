<%-- 
    Document   : booking
    Created on : 29-Nov-2017, 20:04:21
    Author     : Hal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/header.jspf" %>
<div class="main-content">
    <h2>Booking</h2>
    <form method="POST">
    <fieldset  class="content-bg">
        <legend>Rooms</legend>
        <div class="form-card-container">
            <div class="form-card">
                Standard Double Room
                <img src="images/std_d.jpeg" class="room-thumb" alt="Standard Double Room">
                <div class="form-spacing">£? per night</div>
                <label for="checkin" class="form-spacing-small">Quantity</label>
                <input type="number" name="std_d" id="std_d" class="form-spacing" min="0" max="32" value="0">
            </div>
            <div class="form-card">
                Standard Twin Room
                <img src="images/std_t.jpg" class="room-thumb" alt="Standard Twin Room">
                <div class="form-spacing">£? per night</div>
                <label for="checkin" class="form-spacing-small">Quantity</label>
                <input type="number" name="std_d" id="std_t" class="form-spacing" min="0" max="32" value="0">
            </div>
            <div class="form-card">
                Superior Double Room
                <img src="images/sup_d.jpg" class="room-thumb" alt="Superior Double Room">
                <div class="form-spacing">£? per night</div>
                <label for="checkin" class="form-spacing-small">Quantity</label>
                <input type="number" name="std_d" id="sup_d" class="form-spacing" min="0" max="32" value="0">
            </div>
            <div class="form-card">
                Superior Twin Room
                <img src="images/sup_t.jpg" class="room-thumb" alt="Superior Twin Room">
                <div class="form-spacing">£? per night</div>
                <label for="checkin" class="form-spacing-small">Quantity</label>
                <input type="number" name="sup_t" id="sup_t" class="form-spacing" min="0" max="32" value="0">
            </div>
            <div class="form-card">
                <label for="checkin" class="form-spacing-small">Check In Date</label>
                <input type="date" name="checkin" id="checkin" class="form-spacing" required>
                <label for="checkout" class="form-spacing-small">Check Out Date</label>
                <input type="date" name="checkout" id="checkout" class="form-spacing" required>
                <button type="submit" value="submit">Book Rooms</button>
            </div>
        </div>
    </fieldset>
    <fieldset class="content-bg">
        <legend>Payment Details</legend>
        <div class="form-card-container">
            <div class="form-card">
                <input type="text" name="name" id="name" placeholder="Name" class="form-spacing" required>
                <input type="text" name="name" id="email" placeholder="Email Address" class="form-spacing" required>
                <label for="address" class="form-spacing-small">Address</label>
                <textarea id="address" name="address" required></textarea>
                <label for="notes" class="form-spacing-small">Notes</label>
                <textarea id="notes" name="notes"></textarea>
            </div>
            <div class="form-card">
                <input type="text" name="card_no" id="card_no" placeholder="Card Number" class="form-spacing" required>
                <input type="text" name="card_exp" id="card_exp" placeholder="Card Expiry (MM/YY)" pattern="\d{2}\/\d{2}" class="form-spacing" required>
                <label for="address" class="form-spacing-small"></label>
                <select name="card_type" id="card_type">
                    <option value="V" selected>Visa</option>
                    <option value="MC">Mastercard</option>
                    <option value="A">American Express</option>
                </select>
            </div>    
        </div>
      </fieldset>
    </form>
</div>
<%@include file="/WEB-INF/footer.jspf" %>