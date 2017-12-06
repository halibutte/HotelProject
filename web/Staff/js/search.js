function search() {
    //get search text
    var text = $("#search_val").val();
    text = ".*" + text.toLowerCase() + ".*";
    //reset display flags
    $("[data-searchon]").each(function(idx, el) {
        var parent = search_parent(el);
        $(parent).data("show", false);
    });
    $("[data-searchon]").each(function(idx, el) {
        if($(el).data("searchon").toString().toLowerCase().match(text)) {
            var parent = search_parent(el);
            $(parent).data("show", true);
        }
    });
    //show all which are set true
    $("[data-searchon]").each(function(idx, el) {
        //which class is parent?
        var parent =  search_parent(el);
        if($(parent).data("show") === true ) {
            $(parent).show();
        } else {
            $(parent).hide();
        }
    });
}

function search_parent(el) {
    var parentClass = $(el).data("searchparent");
    var parentEl = $(el).closest("."+parentClass).eq(0);
    return parentEl;
}