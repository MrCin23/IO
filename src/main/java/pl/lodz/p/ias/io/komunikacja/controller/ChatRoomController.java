package pl.lodz.p.ias.io.komunikacja.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.komunikacja.dto.CreateChatRoomDTO;
import pl.lodz.p.ias.io.komunikacja.dto.ChatRoomDTO;
import pl.lodz.p.ias.io.komunikacja.dto.MessageDTO;
import pl.lodz.p.ias.io.komunikacja.mapper.ChatRoomMapper;
import pl.lodz.p.ias.io.komunikacja.mapper.MessageMapper;
import pl.lodz.p.ias.io.komunikacja.model.ChatRoom;
import pl.lodz.p.ias.io.komunikacja.model.Message;
import pl.lodz.p.ias.io.komunikacja.service.ChatRoomService;
import pl.lodz.p.ias.io.komunikacja.service.MessageService;
import pl.lodz.p.ias.io.uwierzytelnianie.model.Account;
import pl.lodz.p.ias.io.uwierzytelnianie.services.AuthenticationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;
    private final AuthenticationService authenticationService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatRoomController(ChatRoomService chatRoomService, MessageService messageService, AuthenticationService authenticationService, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatRoomService = chatRoomService;
        this.messageService = messageService;
        this.authenticationService = authenticationService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping("/create")
    public ChatRoomDTO createChatRoom(@RequestBody @Valid CreateChatRoomDTO chatRoomDTO) {
        List<Account> users = authenticationService.getAccountsById(chatRoomDTO.getUsers());
        ChatRoom chatRoom = ChatRoomMapper.toEntity(users, chatRoomDTO.getName());

        ChatRoomDTO createdChatRoomDTO = ChatRoomMapper.toDTO(chatRoomService.createChatRoom(chatRoom));

        for (Account user : users) {
            String userDestination = "/user/" + user.getUsername() + "/queue/chatrooms";
            simpMessagingTemplate.convertAndSend(userDestination, createdChatRoomDTO);
        }

        return createdChatRoomDTO;

        //return ResponseEntity.status(HttpStatus.CREATED).body(createdChatRoomDTO);
    }

    @GetMapping("/{chatRoomId}/history")
    public ResponseEntity<List<MessageDTO>> getChatRoomHistory(@PathVariable Long chatRoomId) {
//        List<Message> messages = chatRoomService.getChatHistory(chatRoomId);
        List<Message> messages = messageService.getMessagesForReceiver(chatRoomId);
        List<MessageDTO> messageDTOList = new ArrayList<>();

        for (Message m : messages)
            messageDTOList.add(MessageMapper.messageToDTO(m));

        return ResponseEntity.ok(messageDTOList);
    }

    @PostMapping("/{chatRoomId}/addUser/{userId}")
    public void addUserToChatRoom(@PathVariable Long chatRoomId, @PathVariable Long userId) {
        ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomId);
        Optional<Account> user = authenticationService.getAccountById(userId);

        if (chatRoom == null || user.isEmpty()) {
            return;
        }

        chatRoom.getUsers().add(user.get());
        chatRoomService.createChatRoom(chatRoom);
    }

    @PostMapping("/{chatRoomId}/deleteUser/{userId}")
    public void deleteUserFromChatRoom(@PathVariable Long chatRoomId, @PathVariable Long userId) {
        ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomId);
        Optional<Account> user = authenticationService.getAccountById(userId);

        if (chatRoom == null || user.isEmpty()) {
            return;
        }

        chatRoom.getUsers().remove(user.get());
        chatRoomService.createChatRoom(chatRoom);
    }

    @MessageMapping("/group/{chatRoomId}")
    @SendTo("/topic/group/{chatRoomId}")
    public Message sendMessage(@DestinationVariable Long chatRoomId, @RequestBody @Valid MessageDTO message) {
        ChatRoom chatRoom = chatRoomService.getChatRoom(chatRoomId);
        if (chatRoom == null) {
            System.out.println("Chat room not found");
            return null;
        }
        Message message1 = MessageMapper.DTOToMessage(message);
        messageService.sendMessage(message1);

        return message1;
    }

    @GetMapping("/user/{userId}")
    public List<ChatRoomDTO> getChatRoomsByUser(@PathVariable Long userId) {
        List<ChatRoom> chatRooms = chatRoomService.getChatRoomsByUserId(userId);
        return ChatRoomMapper.toDTOList(chatRooms);
    }
    //public ResponseEntity<List<ChatRoomDTO>> getChatRooms() {
//        List<ChatRoom> chatRooms = chatRoomService.getChatRooms();
//
//        List<ChatRoomDTO> chatRoomDTOs = ChatRoomMapper.toDTOList(chatRooms);
//
//        return ResponseEntity.ok(chatRoomDTOs);
//    }
}
