/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
   move_room(); 
});

function move_room() {
    var div = $(".message-relocate");
    var flexCont = $("#room_messages");
    var pay = $('#pay_open').length > 0;
    if(pay) {
        $(div).toggleClass('message-error message-confirm');
    }
    $(flexCont).append(div);
    $(div).show();
}

function removeMessage(el) {
    $(el).remove();
}