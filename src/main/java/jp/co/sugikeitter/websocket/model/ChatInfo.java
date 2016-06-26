package jp.co.sugikeitter.websocket.model;

import java.util.List;

import lombok.Data;

@Data
public class ChatInfo<E extends ChatContent> {
    private Type type;

    private List<E> contents;

    public enum Type {
        MESSAGE,
        LOGIN,
        LOGOUT
    }
}
