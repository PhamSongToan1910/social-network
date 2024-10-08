package com.socialNetwork.socialNetwork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialNetwork.socialNetwork.controller.request.AcceptFollowRequest;
import com.socialNetwork.socialNetwork.controller.request.CreatePostRequest;
import com.socialNetwork.socialNetwork.controller.request.GetAllPostRequest;
import com.socialNetwork.socialNetwork.controller.response.PostResponse;
import com.socialNetwork.socialNetwork.controller.response.ResponseData;
import com.socialNetwork.socialNetwork.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/social-network/post")
@RequiredArgsConstructor
@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PostController {

    PostService postService;

    @PostMapping(value = "/create-post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseData<String> createPost(
            @RequestParam("file") MultipartFile file,
            @RequestPart("createPostRequest") String createPostRequestJson) {

        // Parse the JSON string manually
        ObjectMapper objectMapper = new ObjectMapper();
        CreatePostRequest createPostRequest;
        try {
            createPostRequest = objectMapper.readValue(createPostRequestJson, CreatePostRequest.class);
        } catch (JsonProcessingException e) {
            return new ResponseData<>(500, "Error parsing JSON");
        }

        createPostRequest.setImage(file);
        postService.createPost(createPostRequest);
        return new ResponseData<>(200, "Success");
    }

    @GetMapping("/get-all-posts")
    public ResponseData<List<PostResponse>> getAllPosts(@RequestBody GetAllPostRequest getAllPostRequest) {
        return new ResponseData<>(postService.getAllPosts(getAllPostRequest.getPage()));
    }
}
