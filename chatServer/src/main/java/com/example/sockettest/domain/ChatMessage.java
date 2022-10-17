package com.example.sockettest.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    public enum MessageType{
        ENTER, TALK, LEAVE
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
