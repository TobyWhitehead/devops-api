package com.tobywhitehead.devopsapi.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class MessageControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMessage() throws Exception {

        mockMvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "text": "Hello API Test"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Hello API Test"));
    }
}
