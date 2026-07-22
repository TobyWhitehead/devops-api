package com.tobywhitehead.devopsapi.controller;

import com.tobywhitehead.devopsapi.exception.MessageNotFoundException;
import com.tobywhitehead.devopsapi.model.Message;
import com.tobywhitehead.devopsapi.repository.MessageRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private static final Logger logger =
            LoggerFactory.getLogger(MessageController.class);

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

        logger.info("Creating message: {}", message.getText());

        Message saved = repository.save(message);

        logger.info("Created message with id {}", saved.getId());

        return saved;
    }
}
