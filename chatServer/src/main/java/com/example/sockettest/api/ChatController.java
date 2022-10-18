package com.example.sockettest.api;


import com.example.sockettest.domain.ChatRoom;
import com.example.sockettest.domain.DialogDto;
import com.example.sockettest.domain.CreateRoomRequest;
import com.example.sockettest.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestBody CreateRoomRequest request) {
        return chatService.createRoom(request.getRoomId());
    }

    @GetMapping
    public List<DialogDto> getDialog(@RequestBody String roomId) {
        return chatService.getDialog(roomId);
    }

    @DeleteMapping
    public void deleteRoom(@RequestBody CreateRoomRequest request) {
        chatService.deleteRoom(request.getRoomId());
    }

//    @GetMapping
//    public List<ChatRoom> findAllRoom() {
//        return chatService.findAllRoom();
//    }
}
