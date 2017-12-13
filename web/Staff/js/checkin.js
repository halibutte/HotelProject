function toggle_payment(el) {
    //show / hide the payment details on a checkin card
    //el is element creating event
    //get div to be toggled
    var paymentDiv = $(el).closest(".payment-detail-header").find(".payment-details").eq(0);
    if($(paymentDiv).is(":visible")) {
        //payment details visible, so hide
        $(paymentDiv.slideUp());
        $(el).text("[+]");
    } else {
        //not visible, display
        $(paymentDiv).slideDown();
        $(el).text("[-]");
    }
}