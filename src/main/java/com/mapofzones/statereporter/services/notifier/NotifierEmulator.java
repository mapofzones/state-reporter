package com.mapofzones.statereporter.services.notifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
public class NotifierEmulator implements INotifier{

    @Override
    public void sendMessage(String message) {
        log.info(message);
    }
}
