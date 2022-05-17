package com.mapofzones.statereporter.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "notify.telegram")
public class TelegramProperties {

    private String chatId;
    private String token;
    private String apiTelegram;
    private String sendMessagePath;

}
