package com.example.sockettest.service;

import com.example.sockettest.domain.ChatMessage;
import com.example.sockettest.domain.ChatRoom;
import com.example.sockettest.domain.DialogDto;
import com.example.sockettest.persist.DialogRespository;
import com.example.sockettest.persist.entity.Dialog;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;

    private final DialogRespository dialogRespository;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(String roomId) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(roomId)
                .build();
        chatRooms.put(roomId, chatRoom);
        return chatRoom;
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public List<DialogDto> getDialog(String roomId) {
        return dialogRespository.findAllByPartyIdOrderByDtAsc(roomId).stream()
                .map(m -> new DialogDto(m))
                .collect(Collectors.toList());
    }

    public void deleteRoom(String roomId) {
        if (chatRooms.containsKey(roomId)) {
            chatRooms.remove(roomId);
        }
    }

    public void saveMsessage(ChatMessage chatMessage) {
        dialogRespository.save(new Dialog(chatMessage));
    }
}
