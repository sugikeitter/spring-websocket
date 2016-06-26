package jp.co.sugikeitter.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sugikeitter.repository.model.ChatLog;

@Repository
public class ChatLogRepositoryImpl implements ChatLogRepository {

    @Autowired
    private NamedParameterJdbcTemplate npJdbcTemplate;

    @Override
    // TODO トランザクションについて考える
    public int insert(ChatLog chatLog) {
        return npJdbcTemplate.update(
                "INSERT INTO chat_log(id, sender, receiver, message, enabled) " +
                "VALUES(:id, :sender, :receiver, :message, :enabled)",
                new BeanPropertySqlParameterSource(chatLog));
    }

    @Override
    @Transactional(readOnly=true)
    public List<ChatLog> findAllChatLog() {
        return npJdbcTemplate.query(
                "SELECT * FROM chat_log",
                new BeanPropertyRowMapper<ChatLog>(ChatLog.class));
    }
}
