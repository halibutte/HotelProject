/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
       move_room(); 
    //if pay open exists, jump to it
    if($("#pay_anchor").length > 0) {
        $("html, body").animate({
            scrollTop: $("#pay_anchor").offset().top
        }, 200);
    }
    init_settings();
    calc_price();
});

var arrivalSettings = [];
var roomPrices = [];

function move_room() {
    var div = $(".message-relocate");
    var flexCont = $("#message_container");
    var pay = $('#pay_open').length > 0;
    if(pay) {
        $(div).toggleClass('message-error message-confirm');
        flexCont = $("#message_confirm");
    }
    $(flexCont).append(div);
    $(div).show();
}

function removeMessage(el) {
    $(el).remove();
}

function sanity_dates(el) {
    //sanity check the checkin/out dates
    //el is the element prompting check
    var checkin = new Date($("#rooms_check_in").val());
    var checkout = new Date($("#rooms_check_out").val());
    //check if trying to checkout on or before checking
    if(checkout <= checkin) {
        if($(el).attr("id") === "rooms_check_out") {
            //if checkout being changed, set checkin to 1d before
            checkin.setDate(checkout.getDate() - 1);
            var dateStr = formatDate(checkin);
            $("#rooms_check_in").val(dateStr);
        } else {
            //else set to 1d after
            checkout.setDate(checkin.getDate() + 1);
            var dateStr = formatDate(checkout);
            $("#rooms_check_out").val(dateStr);
        }
    }
}

function formatDate(date) {
    //thanks to https://stackoverflow.com/questions/23593052/format-javascript-date-to-yyyy-mm-dd
    //javascript doesn't seem to have a very easy way to format dates to strings
    //and you can't hand a date type to a date input type
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}

function booking_change() {
    hide_payment();
    calc_price();
}

function hide_payment() {
    //check if any of the booking values have been changed, hide the payment section if they have
    //flag to indicate if any changed
    var flag = false;
    var current = array_settings();
    for(var key in arrivalSettings ) {
        if(!(current[key] === arrivalSettings[key])) {
            flag = true;
        }
    }
    if(flag) {
        //hide the payment div
        $("#payment_form").hide();
        $(".message-relocate").remove();
    } else {
        $("#payment_form").show();
    }
}

function init_settings() {
    arrivalSettings = array_settings();
    price_settings();
}

function array_settings() {
    //get rooms, checkin, checkout, custid
    var std_d = $('#std_d').val();
    var std_t = $('#std_t').val();
    var sup_d = $('#sup_d').val();
    var sup_t = $('#sup_t').val();
    var checkin = $('#rooms_check_in').val();
    var checkout = $('#rooms_check_out').val();
    var custid = $('#c_id').val();
    var arr = [];
    arr['std_d'] = std_d;
    arr['std_t'] = std_t;
    arr['sup_d'] = sup_d;
    arr['sup_t'] = sup_t;
    arr['checkin'] = checkin;
    arr['checkout'] = checkout;
    arr['cid'] = custid;
    return arr;
}

function price_settings() {
    $("[data-roomtype]").each(function(idx,el) {
        var type = $(el).data("roomtype");
        var price = $(el).data("roomprice");
        //cast price to number
        price = parseFloat(price);
        //add to array
        roomPrices[type] = price;
    });
}

function calc_price() {
    //get number of rooms desired for each type
    var total = 0;
    for(var key in roomPrices) {
        //get number of rooms of this type desired
        var qty = $("#" + key + "").val();
        if(isNaN(qty)) {
            qty = 0;
        }
        total = total + (qty * roomPrices[key]);
    }
    //now work out number of nights
    var checkin = new Date($("#rooms_check_in").val());
    var checkout = new Date($("#rooms_check_out").val());
    var length = daydiff(checkin, checkout);
    total = total * length;
    $("#cost_span").text(total);
}

function daydiff(first, second) {
    //Thanks to https://stackoverflow.com/questions/542938/how-do-i-get-the-number-of-days-between-two-dates-in-javascript
    return Math.round((second-first)/(1000*60*60*24));
}