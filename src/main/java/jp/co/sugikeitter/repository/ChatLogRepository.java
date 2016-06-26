package jp.co.sugikeitter.repository;

import java.util.List;

import jp.co.sugikeitter.repository.model.ChatLog;

public interface ChatLogRepository {

    int insert(ChatLog chatLog);

    List<ChatLog> findAllChatLog();

}
