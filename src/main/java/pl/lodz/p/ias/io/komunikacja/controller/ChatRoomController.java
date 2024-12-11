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
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;

import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final AuthenticationService authenticationService;

    public ChatRoomController(ChatRoomService chatRoomService, AuthenticationService authenticationService) {
        this.chatRoomService = chatRoomService;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<ChatRoomDTO> createChatRoom(@RequestBody @Valid CreateChatRoomDTO chatRoomDTO) {
        List<Account> users = authenticationService.getAccountsById(chatRoomDTO.getUsers());
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
