package pl.lodz.p.ias.io.komunikacja.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.ias.io.komunikacja.dto.MessageDTO;
import pl.lodz.p.ias.io.komunikacja.mapper.MessageMapper;
import pl.lodz.p.ias.io.komunikacja.model.Message;
import pl.lodz.p.ias.io.komunikacja.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody @Valid MessageDTO messageDTO) {
        Message message = MessageMapper.DTOToMessage(messageDTO);
        MessageDTO savedMessage = MessageMapper.messageToDTO(messageService.sendMessage(message));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

    @GetMapping("/{receiver}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String receiver) {
        return ResponseEntity.ok(messageService.getMessagesForReceiver(receiver));
    }
}