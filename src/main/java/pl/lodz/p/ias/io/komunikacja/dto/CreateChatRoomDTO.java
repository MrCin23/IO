package pl.lodz.p.ias.io.komunikacja.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateChatRoomDTO {
    private List<Long> users;
    private String name;
}
