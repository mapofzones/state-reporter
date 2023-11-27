package com.mapofzones.statereporter.common.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "notify.telegram")
public class TelegramProperties {

    private String token;
    private String apiTelegramOrg;
    private String sendMessagePath;
    private Map<String, ChatProperties> chats;

    @Getter
    @Setter
    public static class ChatProperties {
        private String chatId;
    }
}
