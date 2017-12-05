function toggle_payment(el) {
    //el is element creating event
    //get div to be toggled
    var paymentDiv = $(el).closest(".payment-detail-header").find(".payment-details").eq(0);
    if($(paymentDiv).is(":visible")) {
        $(paymentDiv.slideUp());
        $(el).text("[+]");
    } else {
        $(paymentDiv).slideDown();
        $(el).text("[-]");
    }
}