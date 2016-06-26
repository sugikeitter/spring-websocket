package jp.co.sugikeitter.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.sugikeitter.repository.ChatLogRepository;
import jp.co.sugikeitter.repository.model.ChatLog;
import jp.co.sugikeitter.websocket.model.ChatContent;
import jp.co.sugikeitter.websocket.model.ChatInfo;
import jp.co.sugikeitter.websocket.model.ChatInfo.Type;
import jp.co.sugikeitter.websocket.model.ChatUser;

public class ChatHandler extends TextWebSocketHandler {

    @Autowired
    private ChatLogRepository chatLogRepository;

    /** セッション一覧 */
    private Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    /** ログインユーザ一覧 */
    private Set<String> loginUserSet = new CopyOnWriteArraySet<String>();

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 接続しているセッションとユーザを一覧に追加する
        // sessionMapは同じユーザが複数セッションでアクセスすることも可能であるため、ユーザ名ではなくセッションIDで管理
        this.sessionMap.putIfAbsent(session.getId(), session);
        this.loginUserSet.add(session.getPrincipal().getName());

        // 現在ログイン中のユーザ一覧を取得して送信
        ChatInfo<ChatUser> loginUserInfo = new ChatInfo<>();
        loginUserInfo.setType(Type.LOGIN);
        List<ChatUser> loginUserList = this.getLoginUserList();
        loginUserInfo.setContents(loginUserList);

        this.sendMsa(session, loginUserInfo);

        // 過去のチャットメッセージ一覧を取得して送信
        ChatInfo<ChatLog> chatLogInfo = new ChatInfo<>();
        chatLogInfo.setType(Type.MESSAGE);
        List<ChatLog> chatLogList = chatLogRepository.findAllChatLog();
        chatLogInfo.setContents(chatLogList);

        this.sendMsa(session, chatLogInfo);

        // ログイン通知
        TextMessage message = this.creteTextMsg(Type.LOGIN, session.getPrincipal().getName());
        this.sendMsgAll(message);
    }

    private void sendMsa(WebSocketSession session, ChatInfo<? extends ChatContent> chatInfo) throws IOException {
        String json = mapper.writeValueAsString(chatInfo);
        session.sendMessage(new TextMessage(json));
    }

    private List<ChatUser> getLoginUserList() {
        List<ChatUser> loginUserList = new ArrayList<>();
        for (String userName : loginUserSet) {
            ChatUser chatUser = new ChatUser();
            chatUser.setUserId(userName);
            loginUserList.add(chatUser);
        }
        return loginUserList;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.sessionMap.remove(session.getId());
        this.loginUserSet.remove(session.getPrincipal().getName());
        // ログアウト通知
        TextMessage message = this.creteTextMsg(Type.LOGOUT, session.getPrincipal().getName());

        this.sendMsgAll(message);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatLog chatLog = this.insertChatLog(session, message);
        ChatInfo<ChatLog> newChatMsg = new ChatInfo<>();
        newChatMsg.setType(Type.MESSAGE);
        newChatMsg.setContents(Arrays.asList(chatLog));
        String json = mapper.writeValueAsString(newChatMsg);
        TextMessage sendMsg = new TextMessage(json);

        sendMsgAll(sendMsg);
    }

    // 接続されているセッション（自分も含め）全てにメッセージを送信する
    private void sendMsgAll(TextMessage message) throws Exception {
        for (Entry<String, WebSocketSession> entry : this.sessionMap.entrySet()) {
            entry.getValue().sendMessage(message);
        }
    }

    // ログイン、ログアウト通知用のJSONメッセージを作成する
    private TextMessage creteTextMsg(Type type, String userName) throws JsonProcessingException {
        ChatInfo<ChatUser> chatMessage = new ChatInfo<>();
        chatMessage.setType(type);
        ChatUser chatUser = new ChatUser();
        chatUser.setUserId(userName);
        List<ChatUser> loginNotification = Arrays.asList(chatUser);
        chatMessage.setContents(loginNotification);
        String json = mapper.writeValueAsString(chatMessage);
        return new TextMessage(json);
    }

    /**
     * チャットのメッセージ内容をDBに格納する
     * @param session
     * @param message
     * @return
     */
    private ChatLog insertChatLog(WebSocketSession session, TextMessage message) {
        ChatLog chatLog = new ChatLog();
        chatLog.setId(0L); // MySQLのauto_incrementのため0Lを設定
        chatLog.setSender(session.getPrincipal().getName());
        chatLog.setReceiver(null); // TODO 今後の拡張用
        chatLog.setMessage(message.getPayload());
        chatLog.setEnabled(true);

        chatLogRepository.insert(chatLog);
        return chatLog;
    }
}
