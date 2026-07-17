package com.tobywhitehead.devopsapi.controller;

import com.tobywhitehead.devopsapi.exception.MessageNotFoundException;
import com.tobywhitehead.devopsapi.model.Message;
import com.tobywhitehead.devopsapi.repository.MessageRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MessageControllerTest {

    @Test
    void shouldReturnAllMessages() {

        MessageRepository repository = mock(MessageRepository.class);

        when(repository.findAll()).thenReturn(
                List.of(
                        new Message("Hello"),
                        new Message("DevOps")
                )
        );

        MessageController controller = new MessageController(repository);

        List<Message> messages = controller.getMessages();

        assertEquals(2, messages.size());
    }

    @Test
    void shouldSaveMessage() {

        MessageRepository repository = mock(MessageRepository.class);

        Message message = new Message("Hello");

        when(repository.save(any(Message.class))).thenReturn(message);

        MessageController controller = new MessageController(repository);

        Message result = controller.createMessage(message);

        assertEquals("Hello", result.getText());

        verify(repository).save(message);
    }

    @Test
    void shouldThrowWhenMessageDoesNotExist() {

        MessageRepository repository = mock(MessageRepository.class);

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        MessageController controller = new MessageController(repository);

        assertThrows(
                MessageNotFoundException.class,
                () -> controller.getMessage(99L)
        );
    }
}
