package com.socialNetwork.socialNetwork.utils.googleDriver;

import com.google.api.client.http.InputStreamContent;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileInputStream;
import java.io.IOException;

public class DriveUploader {

    public static String uploadFile(MultipartFile multipartFile) throws IOException {
        Drive driveService = DriveServiceInitializer.initializeDriveService();

        File fileMetadata = new File();
        fileMetadata.setName(multipartFile.getOriginalFilename());

        InputStreamContent mediaContent = new InputStreamContent(multipartFile.getContentType(),
                multipartFile.getInputStream());

        File file = driveService.files().create(fileMetadata, mediaContent)
                .setFields("id, webViewLink, webContentLink")
                .execute();

        com.google.api.services.drive.model.Permission permission = new com.google.api.services.drive.model.Permission();
        permission.setType("anyone");
        permission.setRole("reader");
        driveService.permissions().create(file.getId(), permission).execute();

        String fileUrl = "https://drive.google.com/uc?export=view&id=" + file.getId();

        System.out.println("File ID: " + file.getId());
        System.out.println("View Link: " + file.getWebViewLink());
        System.out.println("Download Link: " + file.getWebContentLink());

        return fileUrl;
    }
}
