$(document).ready(function() {
    run_sums();
    run_avgs();
});

function run_sums() {
    //Work out totals from the different room types
    $("[data-sum]").each(function(idx, el) {
       var colSum = $(el).data("sum");
       //get and sum all the elements of this column
       //get the card which is containing this element
       var card = $(el).closest(".card");
       var sum = 0;
       $(card).find("[data-name='room_breakdown'] .table-row").each(function(idx, el) {
           var thisVal = parseFloat($(el).find(".table-cell").eq(colSum).text().trim());
           if(!isNaN(thisVal)) {
               sum += thisVal;
           }
       });
       $(el).text(sum.toFixed(2));
    });
}

function run_avgs() {
    //Work out totals from the different room types
    $("[data-avg]").each(function(idx, el) {
       var avgCols = $(el).data("avg").toString().split(",");
       //get the total of each col, and divide one by the other
       //get the card which is containing this element
       var card = $(el).closest(".card");
       var num = 0;
       var denom = 0;
       $(card).find("[data-name='room_breakdown'] .table-row").each(function(idx, el) {
           var thisNum = parseFloat($(el).find(".table-cell").eq(avgCols[0]).text().trim());
           var thisDenom = parseFloat($(el).find(".table-cell").eq(avgCols[1]).text().trim());
           if(!isNaN(thisNum)) {
               num += thisNum;
           }
           if(!isNaN(thisDenom)) {
               denom += thisDenom;
           }
       });
       var avg = (num / denom) * 100;
       $(el).text(avg.toFixed(2));
    });
}