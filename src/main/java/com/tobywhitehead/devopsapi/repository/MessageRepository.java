package com.tobywhitehead.devopsapi.repository;

import com.tobywhitehead.devopsapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
