$(document).ready(function() {
    displayMessages();
});

function displayMessages() {
    //get div with messages in
    var messageDiv = $("#message_div");
    //count divs with message class under this
    var messages = $(messageDiv).find("[class*=message]");
    var messageCount = $(messages).length;
    if(messageCount <= 0) {
        $(messageDiv).remove();
    } else if (!$(messageDiv).is(":visible")) {
        $(messageDiv).attr("display", "table-cell");
    }
}

function removeMessage(el) {
    $(el).remove();
    displayMessages();
}