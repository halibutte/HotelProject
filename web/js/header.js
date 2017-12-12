$(document).ready(function() {
   //set the right link element to active
   var href = window.location.href;
   //get the end part
   var parts = href.split("/");
   var end = parts[parts.length-1];
   //loop over li items and see if href matches?
   $(".nav a").each(function(idx, el) {
       var elArr = $(el).attr("href").split("/");
       var thisEnd = elArr[elArr.length-1];
       thisEnd = thisEnd.split("?")[0];
       if(end === thisEnd) {
           $(el).closest("li").addClass("active");
       }
   });
});