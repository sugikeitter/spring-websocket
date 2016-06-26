package jp.co.sugikeitter.repository.model;

import java.io.Serializable;

import jp.co.sugikeitter.websocket.model.ChatContent;
import lombok.Data;

@Data
public class ChatLog implements ChatContent, Serializable {

    private static final long serialVersionUID = 1L;

   private Long id;

   private String sender;

   private String receiver;

   private String message;

   private boolean enabled;

}
