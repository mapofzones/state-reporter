package com.mapofzones.statereporter.common.dto;

import com.mapofzones.statereporter.services.notifier.INotifier;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CheckStatus {

    private Boolean isOk;
    private String message;

    public CheckStatus() {
        this.isOk = true;
        this.message = "";
    }

    public CheckStatus(String message) {
        setMessage(message);
    }

    public void setMessage(String message) {
        if (message == null || message.isBlank()) {
            this.message = "";
            this.isOk = true;
        } else {
            this.message = message;
            this.isOk = false;
        }
    }

    private Boolean isSendable() {
        return (!getIsOk() && !getMessage().isBlank());
    }


    public synchronized void sendMessage(INotifier notifier) {
        if(isSendable()) {
            notifier.sendMessage(getMessage());
        }
    }

    public synchronized void sendMessage(INotifier notifier, String chatId) {
        if (isSendable()) {
            notifier.sendMessage(getMessage(), chatId);
        }
    }

}
