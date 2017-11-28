<!DOCTYPE html>
<html>
<link href="./styles/Main.css" rel="stylesheet" type="text/css"/>
        <meta charset="windows-1252">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
<head>
  <style>
header, footer{
  background-color: #5D5C61;
}
</style>

</head>
<style>
body{
  background-color: #7395AE;

</style>
<body>
<body>
<meta name="viewport" content="width=device-width, initial-scale=1">
<div class="container">

<header>
   <h1> The Heartache Hotel </h1>
</header>
  
<nav>
  <ul>
        <li><a href="Home.html">Home</a></li>
        <li>Book a room with us</li>
        <li><a href="information.html">Information</a></li>
        <li><a href="Events.html">Events</a></li>
        <li><a href="area.html">The local area</a></li>
        <li><a href="about.html">About us</a></li>
        <li><a href="Check_booking.html">Check my booking</a></li>
    </ul>
</nav>
<div>
    <h3>Update Booking</h3>
</div>

<article>
    <form method="POST">
    <fieldset>
    <legend>Customer</legend>
        <br>
        <p>
        <input type="hidden" name="bref">
        Name:<br>
        <input type="text" name="cust_name" disabled>
        <br>
        Email Address:<br>
        <input type="text" name="cust_email">
        <br>
        Address:<br>
        <textarea name="address" name="cust_address">
        </textarea>
        </p>
      <br>
    </fieldset>
    
    <fieldset>
    <legend>Booking</legend>
    <div>
        <p class="inline">
        Checkin Date
        <br>
        <input type="date" name="start_date">
        </p>
        <p class="inline">
        Checkout Date
        <br>
        <input type="date" name="end_date">
        </p>
    </div>
    <div class="clear-all">
        <p>
            Room Type
            <br>
            Standard Double
            <br><br>
            Quantity
            <br>
            <input type="number" name="std_d" step="1" min="0">
        </p>
        <p>
            Room Type
            <br>
            Standard Twin
            <br><br>
            Quantity
            <br>
            <input type="number" name="std_t" step="1" min="0">
        </p>
        <p>
            Room Type
            <br>
            Superior Double
            <br><br>
            Quantity
            <br>
            <input type="number" name="sup_d" step="1" min="0">
        </p>
        <p>
            Room Type
            <br>
            Superior Twin
            <br><br>
            Quantity
            <br>
            <input type="number" name="sup_d" step="1" min="0">
        </p>
    </div>
    </fieldset>
  <fieldset>
            <legend>Card information</legend>
            <p>
		   Card Number:<br>
		   <input type="text" name="card_number">
		   <br>
               <label>Card type</label>
			   <br>
			  
               <select id = "CardList" name="card_type">
                 <option value = "1">Visa</option>
                 <option value = "2">Visa Debt</option>
                 <option value = "3">Delta</option>
                 <option value = "4">Mastercard</option>
               </select>
   			<br>
   		    Card Exp:<br>
   		    <input type="text" name="card_exp">
   		    <br>
            </p>
		
         </fieldset>
  <br>
  <br>
  <input type="submit" value="Submit" name="edit_submit">
</form> 
</article>


<footer>Copyright &copy; University of East Anglia
  <div class="right_link">
    <a href="Reception.html">Reception</a>
    <a href="House_Keeper.html">Room Service</a>
    
  </div>
</footer>

</div>

</body>
</html>
