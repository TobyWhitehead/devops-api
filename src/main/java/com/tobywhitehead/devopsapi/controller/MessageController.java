package com.tobywhitehead.devopsapi.controller;

import com.tobywhitehead.devopsapi.exception.MessageNotFoundException;
import com.tobywhitehead.devopsapi.model.Message;
import com.tobywhitehead.devopsapi.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageRepository repository;

    public MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Message> getMessages() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Message getMessage(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("Message not found"));
    }

    @PostMapping
    public Message createMessage(@Valid @RequestBody Message message) {
        return repository.save(message);
    }
}
