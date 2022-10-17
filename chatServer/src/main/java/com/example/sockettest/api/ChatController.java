package com.example.sockettest.api;


import com.example.sockettest.domain.ChatRoom;
import com.example.sockettest.domain.Dialog;
import com.example.sockettest.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom() {
        return chatService.createRoom();
    }

    @GetMapping
    public List<Dialog> getDialog(@RequestBody String roomId) {
        return chatService.getDialog(roomId);
    }

//    @GetMapping
//    public List<ChatRoom> findAllRoom() {
//        return chatService.findAllRoom();
//    }
}
