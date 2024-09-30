package com.socialNetwork.socialNetwork.controller;

import com.socialNetwork.socialNetwork.controller.request.CreateReactRequest;
import com.socialNetwork.socialNetwork.controller.response.ResponseData;
import com.socialNetwork.socialNetwork.service.ReactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/social-network/react")
@RequiredArgsConstructor
@RestController
public class ReactController {

    private final ReactService reactService;

    @PostMapping("/create-react")
    public ResponseData<String> createReact(@RequestBody CreateReactRequest request) {
        reactService.reactPost(request);
        return new ResponseData<>(200, "Success");
    }

    @PostMapping("/update-react")
    public ResponseData<String> updateReact(@RequestBody CreateReactRequest request) {
        reactService.updateReactPost(request);
        return new ResponseData<>(200, "Success");
    }
}
