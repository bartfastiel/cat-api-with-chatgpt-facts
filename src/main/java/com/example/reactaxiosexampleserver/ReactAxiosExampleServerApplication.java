package com.example.reactaxiosexampleserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ReactAxiosExampleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactAxiosExampleServerApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("https://meowfacts.herokuapp.com/")
                .build();
    }
}
