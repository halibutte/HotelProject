$(document).ready(function() {
    displayMessages();
});

function displayMessages() {
    //display error / confirmation messages
    //get div with messages in
    var messageDiv = $("#message_div");
    //count divs with message class under this
    var messages = $(messageDiv).find("[class*=message]");
    var messageCount = $(messages).length;
    if(messageCount <= 0) {
        //if there are no messages, remove the message column altogether
        $(messageDiv).remove();
    } else if (!$(messageDiv).is(":visible")) {
        //if there are messages, display this column
        $(messageDiv).attr("display", "table-cell");
    }
}

function removeMessage(el) {
    //remove a message onclick
    $(el).remove();
    displayMessages();
}