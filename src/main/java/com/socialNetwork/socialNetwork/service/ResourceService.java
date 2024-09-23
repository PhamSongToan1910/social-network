package com.socialNetwork.socialNetwork.service;

import com.socialNetwork.socialNetwork.entities.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {
    String UploadeFile(MultipartFile file);
}
