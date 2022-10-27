package com.example.sockettest.api;


import com.example.sockettest.domain.*;
import com.example.sockettest.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestBody CreateRoomRequest request) {
        return chatService.createRoom(request.getRoomId());
    }

    @GetMapping
    public List<DialogDto> getDialog(@RequestBody GetDialogRequest request) {
        return chatService.getDialog(request.getRoomId());
    }

    @DeleteMapping
    public void deleteRoom(@RequestBody DeleteRoomRequest request) {
        chatService.deleteRoom(request.getRoomId());
    }

//    @GetMapping
//    public List<ChatRoom> findAllRoom() {
//        return chatService.findAllRoom();
//    }
}
