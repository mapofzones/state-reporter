package com.mapofzones.statereporter.common.dto;

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
}
