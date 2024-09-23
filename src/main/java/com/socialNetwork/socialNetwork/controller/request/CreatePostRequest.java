package com.socialNetwork.socialNetwork.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest extends PostRequest{

    private MultipartFile image;
}
