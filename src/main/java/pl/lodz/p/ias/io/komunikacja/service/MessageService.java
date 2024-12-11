package pl.lodz.p.ias.io.komunikacja.service;

import org.springframework.stereotype.Service;
import pl.lodz.p.ias.io.komunikacja.model.Message;
import pl.lodz.p.ias.io.komunikacja.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getMessagesForReceiver(String receiver) {
        return messageRepository.findByReceiver(receiver);
    }
}
