$(document).ready(function() {
    $("select").change(function() {
       update_price($(this));
    });
    update_price($("select"));
    highlight_updated();
});
function update_price(event) {
    //when the type of item to be added to a bill is changed, set the price
    //input to be the default price for this type
    //el is the combobox calling this
    var selElem = $(event).find(":selected");
    var price = $(selElem).data("defprice");
    //format to 2dp
    price = parseFloat(price).toFixed(2);
    $(selElem).closest("form").find("[name='item_price']").val(price);
}
function highlight_updated() {
    //this highlights parts of the booking card which was just updated for a few
    //seconds, to show which was affected.
    //elements to be highlight should have the the highlightnum data attribute
    var highlight = $("#guest_container").data("highlightchild");
    if(!isNaN(highlight)) {
        var el = $("#guest_container").find("[data-highlightnum="+highlight+"]");
        //turn it green
        $(el).addClass("message-confirm");
        //call a function to remove green class in 1.5secs
        setTimeout(function() {
            $(el).removeClass("message-confirm");
        }, 1500);
    }
}