package com.mapofzones.statereporter.config.notifier;

import com.mapofzones.statereporter.common.properties.TelegramProperties;
import com.mapofzones.statereporter.services.notifier.INotifier;
import com.mapofzones.statereporter.services.notifier.TelegramNotifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "notify.emulator.enabled", havingValue = "false")
public class TelegramNotifierConfig {

    @Bean
    public TelegramProperties telegramProperties() {
        return new TelegramProperties();
    }

    @Bean
    public INotifier telegramNotifier(TelegramProperties telegramProperties) {
        return new TelegramNotifier(telegramProperties);
    }
}
