package pl.lodz.p.ias.io.komunikacja;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.lodz.p.ias.io.komunikacja.dto.MessageDTO;
import pl.lodz.p.ias.io.komunikacja.mapper.MessageMapper;
import pl.lodz.p.ias.io.komunikacja.model.Message;
import pl.lodz.p.ias.io.komunikacja.repository.MessageRepository;
import pl.lodz.p.ias.io.komunikacja.service.MessageService;

import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MessageTest {
    @Mock
    private MessageRepository messageRepository;

    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageService = new MessageService(messageRepository);
    }

    @Test
    public void sendMessageTest() {
        MessageDTO dto = new MessageDTO("sender", "receiver", "content", new Date());
        Message message = MessageMapper.DTOToMessage(dto);

        Mockito.when(messageRepository.save(Mockito.any(Message.class))).thenReturn(message);

        MessageDTO messageDTO = MessageMapper.messageToDTO(messageService.sendMessage(message));
        Assertions.assertEquals(dto.getSender(), messageDTO.getSender());
    }

    @Test
    public void getMessagesForReceiverTest() {
        Message message = new Message();
        message.setSender("sender");
        message.setReceiver("receiver");
        message.setContent("content");
        message.setTimestamp(new Date());

        Mockito.when(messageRepository.findByReceiver("receiver")).thenReturn(List.of(message));

        List<Message> result = messageService.getMessagesForReceiver("receiver");

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("sender", result.get(0).getSender());
    }
}
