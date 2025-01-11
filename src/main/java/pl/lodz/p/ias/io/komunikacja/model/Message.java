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
    private Long chatId;
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Message(String content, long senderId, long chatId, Date date) {
        this.content = content;
        this.senderId = senderId;
        this.chatId = chatId;
        this.timestamp = date;
        if (date == null) //TODO usunac jak zrobi sie front
            this.timestamp = new Date();
    }
}
