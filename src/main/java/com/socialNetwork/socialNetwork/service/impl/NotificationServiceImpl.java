package com.socialNetwork.socialNetwork.service.impl;

import com.socialNetwork.socialNetwork.entities.Notification;
import com.socialNetwork.socialNetwork.entities.User;
import com.socialNetwork.socialNetwork.repository.interfacePackage.NotificationRepository;
import com.socialNetwork.socialNetwork.repository.interfacePackage.UserRepository;
import com.socialNetwork.socialNetwork.service.NotificationService;
import com.socialNetwork.socialNetwork.utils.Constant;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationServiceImpl implements NotificationService {

    NotificationRepository notificationRepository;
    UserRepository userRepository;
    MongoTemplate mongoTemplate;

    @Override
    public void sendMessageToAllUsers(String userId, String message, List<String> userIdReceiver) {
        List<Notification> notificationList = userIdReceiver.stream().map((id) -> sendMessageToUser(userId, message, id)).toList();
        saveNotification(notificationList);
    }

    @Override
    public Notification sendMessageToUser(String userIdSender, String message, String userIdReceiver) {
        try {
            User userReceiver = getPlayerIdByUserId(userIdReceiver);
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", Constant.PUSH_NOTIFICATION.REST_API_KEY);
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \""+ Constant.PUSH_NOTIFICATION.APP_ID +"\","
                    +   "\"include_player_ids\": [\""+ userReceiver.getPlayerId() +"\"],"
                    +   "\"data\": {\"foo\": \"bar\"},"
                    +   "\"contents\": {\"en\": \""+ message +"\"}"
                    + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            jsonResponse = mountResponseRequest(con, httpResponse);
            System.out.println("jsonResponse:\n" + jsonResponse);
            return new Notification(userIdSender, userReceiver.getPlayerId(), message, userIdReceiver, Constant.NOTIFICATION_TYPE.FOLLOW_REQUEST);
        } catch(Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    private static String mountResponseRequest(HttpURLConnection con, int httpResponse) throws IOException {
        String jsonResponse;
        if (  httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        else {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        return jsonResponse;
    }

    private User getPlayerIdByUserId(String userId) {
        return userRepository.findById(userId, User.class);
    }

    private void saveNotification(List<Notification> listNotifications) {
        mongoTemplate.insertAll(listNotifications);
    }
}
