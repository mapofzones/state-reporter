package com.mapofzones.statereporter.processor;

import com.mapofzones.statereporter.data.constants.TelegramConstants;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TelegramNotifier {
    public static List<HttpResponse<String>> sendMessage(String message) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        List<HttpResponse<String>> response = new ArrayList<>();
        for (int i = 0; i < message.length(); i += 4096) {
            int endIndex = Math.min(i + 4096, message.length());
            response.add(sendMessageByParts(message.substring(i, endIndex), client));
        }
        return response;
    }

    private static HttpResponse<String> sendMessageByParts(String message, HttpClient client) throws IOException, InterruptedException {
        System.out.println("CHAT_ID: " + TelegramConstants.CHAT_ID);
        System.out.println("TOKEN: " + TelegramConstants.TOKEN);
        String chatId = "${checker.telegram-chat-id}";
        System.out.println("chat id: " + chatId);
        UriBuilder builder = UriBuilder
                .fromUri(TelegramConstants.API_TELEGRAM_ORG)
                .path(TelegramConstants.SEND_MESSAGE_PATH)
                .queryParam("chat_id", TelegramConstants.CHAT_ID)
                .queryParam("text", message)
                .queryParam("parse_mode", "html");

        String token = "${checker.telegram-token-id}";
        System.out.println("token: " + token);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + TelegramConstants.TOKEN))
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }
}
