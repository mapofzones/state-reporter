package com.mapofzones.statereporter.config.notifier;

import com.mapofzones.statereporter.services.notifier.INotifier;
import com.mapofzones.statereporter.services.notifier.NotifierEmulator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "notify.emulator.enabled", havingValue = "true")
public class NotifierEmuConfig {

    @Bean
    public INotifier notifierEmulator() {
        return new NotifierEmulator();
    }

}
