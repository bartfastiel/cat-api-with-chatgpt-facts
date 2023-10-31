package com.example.reactaxiosexampleserver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatFactService {

    private final ChatGptService chatGptService;

    public CatFact getCatFact() {
        return new CatFact(chatGptService.getChatGPTResponse("Tell me a funny cat fact!"));
    }

    public String getStory(Cat cat) {
        return chatGptService.getChatGPTResponse("Tell me a story about the cat "+ cat.name() + ", of breed " + cat.breed() + " who is " + cat.age() + " years old. Roughly 100 words.");
    }
}
