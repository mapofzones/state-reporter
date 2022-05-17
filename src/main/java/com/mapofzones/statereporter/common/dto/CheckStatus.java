package com.mapofzones.statereporter.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CheckStatus {

    Boolean isOk;
    String message;

    public CheckStatus() {
        this.isOk = false;
        this.message = "";
    }
    public CheckStatus(Boolean isOk) {
        this.isOk = isOk;
        this.message = "";
    }

    public CheckStatus(Boolean isOk, String message) {
        this.isOk = isOk;
        this.message = message;
    }
}
