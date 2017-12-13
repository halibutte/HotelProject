$(document).ready(function() {
    show_empty();
});

function show_empty() {
    //when there are empty flexcontainers, show a message explaining they
    //are empty rather than broken. empty class defines the div to show for
    //this flexcontainer
    $(".empty").each(function(idx, el) {
        //get the flex container parent of this empty message
       var flexContainer = $(el).closest(".flexCont");
       //count the number of normal items, display empty if 0 or less
       var flexItems = $(flexContainer).find(".flexItem");
       if($(flexItems).length <= 0) {
           $(this).show();
       } else {
           $(this).hide();
       }
    });
    $(".empty-hide").show();
}