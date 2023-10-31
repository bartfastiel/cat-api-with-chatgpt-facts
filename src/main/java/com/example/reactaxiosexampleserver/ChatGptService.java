package com.example.reactaxiosexampleserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Service
public class ChatGptService {

    @Value("${app.openai-api-key}")
    private String openaiApiKey;

    private record ChatGPTMessage(
            String role,
            String content
    ) {
    }

    private record ChatGPTRequest(
            String model,
            List<ChatGPTMessage> messages
    ) {
        ChatGPTRequest(String message) {
            this("gpt-3.5-turbo", Collections.singletonList(new ChatGPTMessage("user", message)));
        }
    }

    private record ChatGPTChoice(
            ChatGPTMessage message
    ) {
    }

    private record ChatGPTResponse(
            List<ChatGPTChoice> choices
    ) {
        public String text() {
            return choices.get(0).message().content();
        }
    }

    public String getChatGPTResponse(String message) {
        return WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader("Authorization", "Bearer " + openaiApiKey)
                .build()
                .post()
                .bodyValue(new ChatGPTRequest(message))
                .retrieve()
                .bodyToMono(ChatGPTResponse.class)
                .map(ChatGPTResponse::text)
                .block();
    }
}
