package com.socialNetwork.socialNetwork.utils.googleDriver;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class DriveServiceInitializer {

    public static Drive initializeDriveService() throws IOException {
        try (InputStream serviceAccountStream = new FileInputStream("stone-facility-438408-n8-7e6be8a6d826.json")) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccountStream)
                    .createScoped(Arrays.asList(DriveScopes.DRIVE));

            return new Drive.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    new HttpCredentialsAdapter(credentials)
            ).setApplicationName("social-network")
                    .build();
        }
    }
}
