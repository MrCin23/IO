package pl.lodz.p.ias.io.komunikacja.service;

import pl.lodz.p.ias.io.komunikacja.model.Message;

import java.util.List;

public interface IMessageService{
    Message sendMessage(Message message);

    List<Message> getMessagesForReceiver(long chatId);
}
