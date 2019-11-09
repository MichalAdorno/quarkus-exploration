var connected = false;
var socket;
$( document ).ready(function() {
  $("#connect").click(connect);
  $("#send").click(sendMessage);
  $("#userFrom").keypress(function(event){
      if(event.keyCode == 13 || event.which == 13) {
          connect();
      }
  });
  $("#msg").keypress(function(event) {
      if(event.keyCode == 13 || event.which == 13) {
          sendMessage();
      }
  });
$("#chat").change(function() {
    scrollToBottom();
  });
  $("#userFrom").focus();
});
var connect = function() {
  if (! connected) {
      var userFrom = $("#userFrom").val();
      var usersTo = $("#usersTo").val();
      console.log("Endpoint: " + "ws://" + location.host + "/application/chat/" + userFrom + "/" + usersTo);
      socket = new WebSocket("ws://" + location.host + "/application/chat/" + userFrom + "/" + usersTo);
      socket.onopen = function() {
          connected = true;
          console.log("Connected to the web socket");
          $("#send").attr("disabled", false);
          $("#connect").attr("disabled", true);
          $("#userFrom").attr("disabled", true);
          $("#msg").focus();
      };
      socket.onmessage =function(m) {
          console.log("Got message: " + m.data);
          $("#chat").append(m.data + "\n");
          scrollToBottom();
      };
  }
};
var sendMessage = function() {
  if (connected) {
      var value = $("#msg").val();
      console.log("Sending " + value);
      socket.send(value);
      $("#msg").val("");
  }
};
var scrollToBottom = function () {
$('#chat').scrollTop($('#chat')[0].scrollHeight);
};