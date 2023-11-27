package com.mapofzones.statereporter.services.notifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class NotifierEmulator implements INotifier{

    @Async
    @Override
    public void sendMessage(String message) {
        log.info(message);
    }

    @Async
    @Override
    public void sendMessage(String message, String chatId) {
        log.info(message,chatId);
    }
}
