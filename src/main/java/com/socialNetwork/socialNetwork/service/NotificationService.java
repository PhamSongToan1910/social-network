package com.socialNetwork.socialNetwork.service;

import com.socialNetwork.socialNetwork.entities.Notification;

import java.util.List;

public interface NotificationService {
    public void sendMessageToAllUsers(String listUserIds, String message, List<String> userIdReceiver);
    public Notification sendMessageToUser(String userIdSender, String message, String userIdReceiver);
}
