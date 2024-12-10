package pl.lodz.p.ias.io.komunikacja.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.komunikacja.dto.CreateChatRoomDTO;
import pl.lodz.p.ias.io.komunikacja.dto.ChatRoomDTO;
import pl.lodz.p.ias.io.komunikacja.mapper.ChatRoomMapper;
import pl.lodz.p.ias.io.komunikacja.model.ChatRoom;
import pl.lodz.p.ias.io.komunikacja.model.MockUser;
import pl.lodz.p.ias.io.komunikacja.service.ChatRoomService;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @PostMapping
    public ResponseEntity<ChatRoomDTO> createChatRoom(@RequestBody @Valid CreateChatRoomDTO chatRoomDTO) {
//        List<User> users = userService.getUsersByIds(chatRoomDTO.getUsers());
        List<MockUser> users = List.of(new MockUser(123L, "user1"), new MockUser(456L, "user2")); // TODO: replace with actual User objects
        ChatRoom chatRoom = ChatRoomMapper.toEntity(users);

        ChatRoomDTO createdChatRoomDTO = ChatRoomMapper.toDTO(chatRoomService.createChatRoom(chatRoom));

        return ResponseEntity.status(HttpStatus.CREATED).body(createdChatRoomDTO);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomDTO>> getChatRooms() {
        List<ChatRoom> chatRooms = chatRoomService.getChatRooms();

        List<ChatRoomDTO> chatRoomDTOs = ChatRoomMapper.toDTOList(chatRooms);

        return ResponseEntity.ok(chatRoomDTOs);
    }
}
