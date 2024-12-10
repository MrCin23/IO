package pl.lodz.p.ias.io.komunikacja;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.lodz.p.ias.io.komunikacja.dto.ChatRoomDTO;
import pl.lodz.p.ias.io.komunikacja.dto.CreateChatRoomDTO;
import pl.lodz.p.ias.io.komunikacja.mapper.ChatRoomMapper;
import pl.lodz.p.ias.io.komunikacja.model.ChatRoom;
import pl.lodz.p.ias.io.komunikacja.repository.ChatRoomRepository;
import pl.lodz.p.ias.io.komunikacja.service.ChatRoomService;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ChatRoomTest {
    @Mock
    private ChatRoomRepository chatRoomRepository;

    private ChatRoomService chatRoomService;

    @BeforeEach
    void setUp() {
        chatRoomService = new ChatRoomService(chatRoomRepository);
    }

    @Test
    public void createChatRoomTest() {
        List<User> users = List.of("user1", "user2"); // TODO: replace with actual User objects
        CreateChatRoomDTO createChatRoomDTO = new CreateChatRoomDTO(users);

        ChatRoom chatRoom = ChatRoomMapper.toEntity(users);

        Mockito.when(chatRoomRepository.save(Mockito.any(ChatRoom.class))).thenReturn(chatRoom);

        ChatRoomDTO createdChatRoomDTO = ChatRoomMapper.toDTO(chatRoomService.createChatRoom(chatRoom));
        Assertions.assertEquals(createChatRoomDTO.getUsers(), createdChatRoomDTO.getUsers());
    }
}
