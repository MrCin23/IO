package pl.lodz.p.ias.io.komunikacja.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;
    private String senderName;
    private Long chatId;
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Message(String content, String senderName, long senderId, long chatId, Date date) {
        this.content = content;
        this.senderName = senderName;
        this.senderId = senderId;
        this.chatId = chatId;
        this.timestamp = date;
        if (date == null) //TODO usunac jak zrobi sie front
            this.timestamp = new Date();
    }
}
