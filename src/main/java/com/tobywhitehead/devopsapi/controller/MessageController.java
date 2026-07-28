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

        logger.debug("Retrieving all messages");

        List<Message> messages = repository.findAll();

        logger.debug("Retrieved {} messages", messages.size());

        return messages;
    }

    @GetMapping("/{id}")
    public Message getMessage(@PathVariable Long id) {

        logger.debug("Retrieving message with id {}", id);

        Message message = repository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("Message with id " + id + " not found"));

        logger.debug("Successfully retrieved message with id {}", id);

        return message;
    }

    @PostMapping
    public Message createMessage(@Valid @RequestBody Message message) {

        logger.info("Creating message");

        Message saved = repository.save(message);

        logger.info("Created message with id {}", saved.getId());

        return saved;
    }
}
