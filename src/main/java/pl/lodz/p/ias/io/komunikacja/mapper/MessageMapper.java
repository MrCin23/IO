package pl.lodz.p.ias.io.komunikacja.mapper;

import pl.lodz.p.ias.io.komunikacja.dto.MessageDTO;
import pl.lodz.p.ias.io.komunikacja.model.Message;

public class MessageMapper {

    public static Message DTOToMessage(MessageDTO messageDTO) {
        return new Message(messageDTO.getContent(), messageDTO.getChatId(), messageDTO.getSenderId(), messageDTO.getTimestamp());
    }

    public static MessageDTO messageToDTO(Message message) {
        return new MessageDTO(message.getSenderId(), message.getChatId(), message.getContent(), message.getTimestamp());
    }
}
