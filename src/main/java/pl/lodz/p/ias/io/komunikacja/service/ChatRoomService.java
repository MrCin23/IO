package pl.lodz.p.ias.io.komunikacja.service;

import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.komunikacja.model.ChatRoom;
import pl.lodz.p.ias.io.komunikacja.model.Message;
import pl.lodz.p.ias.io.komunikacja.repository.ChatRoomRepository;

import java.util.List;

@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    public ChatRoom updateChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    public List<Message> getChatHistory(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).map(ChatRoom::getMessages).orElse(null);
    }

    public List<ChatRoom> getChatRooms() {
        return chatRoomRepository.findAll();
    }

    public ChatRoom getChatRoom(Long id) {
        return chatRoomRepository.findById(id).orElse(null);
    }
}
