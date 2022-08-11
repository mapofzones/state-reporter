package com.mapofzones.statereporter.services.notifier;

import com.mapofzones.statereporter.common.properties.TelegramProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
public class TelegramNotifier implements INotifier{

    private final TelegramProperties telegramProperties;

    public TelegramNotifier(TelegramProperties telegramProperties) {
        this.telegramProperties = telegramProperties;
    }

    @Async
    @Override
    public void sendMessage(String message) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        for (int i = 0; i < message.length(); i += 4096) {
            int endIndex = Math.min(i + 4096, message.length());
            sendMessageByParts(message.substring(i, endIndex), client);
        }
    }

    private void sendMessageByParts(String message, HttpClient client) {

        log.info("Send message to Telegram");
        log.info("CHAT_ID: *****");
        log.info("TOKEN: *****");

        UriBuilder builder = UriBuilder
                .fromUri(telegramProperties.getApiTelegramOrg())
                .path(telegramProperties.getSendMessagePath())
                .queryParam("chat_id", telegramProperties.getChatId())
                .queryParam("text", message)
                .queryParam("parse_mode", "html");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + telegramProperties.getToken()))
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info(String.valueOf(response.statusCode()));
            log.info(response.body());

        } catch (IOException | InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
