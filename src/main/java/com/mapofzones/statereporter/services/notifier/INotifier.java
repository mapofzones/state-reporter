package com.mapofzones.statereporter.services.notifier;

public interface INotifier {

    void sendMessage(String message);
    void sendMessage(String message, String chatId);

}
