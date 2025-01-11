package pl.lodz.p.ias.io.komunikacja.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class MessageDTO {
    @NotNull(message = "Sender cannot be empty")
    private long senderId;
    @NotNull(message = "Receiver cannot be empty")
    private long chatId;
    @NotEmpty(message = "Content cannot be empty")
    private String content;
    @NotNull(message = "Timestamp cannot be null")
    private Date timestamp;
}
