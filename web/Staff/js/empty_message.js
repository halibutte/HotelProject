$(document).ready(function() {
    show_empty();
});

function show_empty() {
    $(".empty").each(function(idx, el) {
       var flexContainer = $(el).closest(".flexCont");
       var flexItems = $(flexContainer).find(".flexItem");
       if($(flexItems).length <= 0) {
           $(this).show();
       } else {
           $(this).hide();
       }
    });
    $(".empty-hide").show();
}