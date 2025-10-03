package ru.otus.hw.config;

import chat.giga.client.auth.AuthClient;
import chat.giga.client.GigaChatClient;
import chat.giga.client.auth.AuthClientBuilder.OAuthBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static chat.giga.model.Scope.GIGACHAT_API_PERS;

@Configuration
public class GigaChatConfig {

    @Value("${gpt.gigachat.auth-key}")
    private String authKey;

    @Bean
    public GigaChatClient getGigaChatClient() {
        return GigaChatClient.builder()
                .verifySslCerts(false)
                .authClient(AuthClient.builder()
                        .withOAuth(OAuthBuilder.builder()
                                .authKey(authKey)
                                .scope(GIGACHAT_API_PERS) // Или другой необходимый scope
                                .build())
                        .build())
                .build();
    }
}