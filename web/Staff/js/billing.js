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