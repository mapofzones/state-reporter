package com.mapofzones.zoneheightchecker.data.constants;

public interface TelegramConstants {
    String CHAT_ID = System.getenv("CHAT_ID");
    String TOKEN = System.getenv("TOKEN");
    String API_TELEGRAM_ORG = "https://api.telegram.org";
    String SEND_MESSAGE_PATH = "/{token}/sendMessage";
}
