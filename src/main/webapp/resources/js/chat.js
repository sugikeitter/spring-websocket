$(function() {
    /**
     * WebSocketの設定
     */
    var ws = new WebSocket("ws://localhost:8080/sugikeitter/websocket/chat");
    ws.onopen = function() {
        $("#status").css("color", "green");
    };

    ws.onclose = function() {
        $("#status").css("color", "red");
    }

    ws.onmessage = function(message) {
        var json = $.parseJSON(message.data);
        execJson(json);
    }
    ws.onerror = function(event) {
        alert("エラー");
    }

    $("#form").submit(function() {
        ws.send($("#message").val());
        $("#message").val("");
        return false;
    });

    function execJson(json) {
        if (json.type == "MESSAGE") {
            json.contents.forEach(function(chatLog) {
                // ユーザ名とメッセージを表示
                if (chatLog.sender == $("#userName").text()) {
                    addMyMessage(chatLog);
                } else {
                    addMessage(chatLog);
                }
            });
            
        } else if (json.type == "LOGIN") {
            json.contents.forEach(function(loginUser) {
                var userName = "." + loginUser.userId;
                $(userName).css("color", "blue");
            });
        } else if (json.type == "LOGOUT") {
            json.contents.forEach(function(loginUser) {
                var userName = "." + loginUser.userId;
                $(userName).css("color", "black");
            });
        }
    }

    function addMessage(chatLog) {
        var userAndMsg = '<div class="chat-box">'
                         + '<div class="chat-face">'
                         + '<img src="" alt="' + chatLog.sender + '" width="90" height="90">'
                         + '</div>'
                         + '<div class="chat-area">'
                         + '<div class="chat-message">'
                         + chatLog.message
                         + '</div></div></div>';
        $("#log").append(userAndMsg);
    }

    function addMyMessage(chatLog) {
        var userAndMsg = '<div class="chat-box">'
                         + '<div class="chat-face-myself">'
                         + '<img src="" alt="' + chatLog.sender + '" width="90" height="90">'
                         + '</div>'
                         + '<div class="chat-area-myself">'
                         + '<div class="chat-message-myself">'
                         + chatLog.message
                         + '</div></div></div>';
        $("#log").append(userAndMsg);
    }
});