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

    private String sender;
    private String receiver;
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public Message(String content, String receiver, String sender, Date date) {
        this.content = content;
        this.receiver = receiver;
        this.sender = sender;
        this.timestamp = date;
        if (date == null) //TODO usunac jak zrobi sie front
            this.timestamp = new Date();
    }
}
