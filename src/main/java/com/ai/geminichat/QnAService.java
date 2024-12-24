package com.ai.geminichat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class QnAService {

    //access to api and url [gemini]

    @Value("${gemini.api.url}")

    private String geminiAPIURL;
    @Value("${gemini.api.key}")

    private String geminiAPIKEY;

    private final WebClient webClient;

    public QnAService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    public String getAnswer(String question){
        //construct the request payload
        Map<String,Object> requestBody = Map.of(
                "contents",new Object[]{
                        Map.of(
                                "parts", new Object[]{
                                        Map.of("text",question)
                                }
                        )
                }
        );


//{"contents":[{"parts":[{"text":[{"text":"what are computers?"}]}]]}
        //make api call
String response =webClient.post()
        .uri(geminiAPIURL+geminiAPIKEY)
        .header("Content-Type","application/json")
        .bodyValue(requestBody)
        .retrieve()
        .bodyToMono(String.class)
        .block();
        //return response
        return response;
    }
}
