$(document).ready(function() {
    $("select").change(function() {
       update_price($(this));
    });
    update_price($("select"));
    highlight_updated();
});
function update_price(event) {
    //el is the combobox calling this
    var selElem = $(event).find(":selected");
    var price = $(selElem).data("defprice");
    price = parseFloat(price).toFixed(2);
    $(selElem).closest("form").find("[name='item_price']").val(price);
}
function highlight_updated() {
    var highlight = $("#guest_container").data("highlightchild");
    if(!isNaN(highlight)) {
        var el = $("#guest_container").find("[data-highlightnum="+highlight+"]");
        $(el).addClass("message-confirm");
        setTimeout(function() {
            $(el).removeClass("message-confirm");
        }, 1500);
    }
}