package jp.co.sugikeitter.websocket.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ChatUser implements ChatContent, Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

}
