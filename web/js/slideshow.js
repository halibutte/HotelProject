//sourced from https://www.w3schools.com/w3css/w3css_slideshow.asp
$(document).ready(function () {
    showDivs(slideIndex);
    window.setInterval(function() {
        plusDivs(1);
    }, 10000);
});

var slideIndex = 1;
function plusDivs(n) {
    showDivs(slideIndex += n);
}

function showDivs(n) {
    var i;
    var x = document.getElementsByClassName("slide");
    if (n > x.length) {slideIndex = 1}    
    if (n < 1) {slideIndex = x.length}
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";  
    }
    x[slideIndex-1].style.display = "block";  
}