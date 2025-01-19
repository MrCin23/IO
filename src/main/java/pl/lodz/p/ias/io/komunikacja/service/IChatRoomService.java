package pl.lodz.p.ias.io.komunikacja.service;

import pl.lodz.p.ias.io.komunikacja.model.ChatRoom;
import pl.lodz.p.ias.io.komunikacja.model.Message;

import java.util.List;

public interface IChatRoomService {
    ChatRoom createChatRoom(ChatRoom chatRoom);

    ChatRoom updateChatRoom(ChatRoom chatRoom);

    List<Message> getChatHistory(Long chatRoomId);

    List<ChatRoom> getChatRooms();

    ChatRoom getChatRoom(Long chatRoomId);

    List<ChatRoom> getChatRoomsByUserId(Long userId);
}
