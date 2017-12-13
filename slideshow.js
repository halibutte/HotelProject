$(document).ready(function () {
    showDivs(slideIndex);
});

var slideIndex = 1; //First image
function plusDivs(n) {
    showDivs(slideIndex += n); //Add to slide Index
}

function showDivs(n) //Display first image 
{
    var i;
    var x = document.getElementsByClassName("slide");
    if (n > x.length) {slideIndex = 1}    
    if (n < 1) {slideIndex = x.length}
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";  
    }
    x[slideIndex-1].style.display = "block";  //Display format as block
}