
var stompClient = null;

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $('#connect').click(function() { connect(); });
    $('#disconnect').click(function() { disconnect(); });
    $('#clear_monitor').click(function() { clearMonitor(); });

});


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#monitor").show();
    }
    else {
        $("#monitor").hide();
    }
}

function connect() {

    var host = document.getElementById("host").value;
    var channel = document.getElementById("channel").value;

    var socket = new SockJS(host);
    stompClient = Stomp.over(socket);

    stompClient.connect({ // Headers
            "channel" : channel
        },
        function (frame) { // ConnectCallback
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/user/topic/reply', function(response) {

                showContent(response);

            });
        }
    );

}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function clearMonitor() {
    $('#messages-list').empty();
}

function showContent(response) {

    console.log(response);
    $('#messages-list').prepend('<li>' + response.body + '</li>');

}