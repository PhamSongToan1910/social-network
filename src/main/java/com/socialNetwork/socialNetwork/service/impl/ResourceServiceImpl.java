package com.socialNetwork.socialNetwork.service.impl;

import com.socialNetwork.socialNetwork.entities.Resource;
import com.socialNetwork.socialNetwork.repository.impl.ResourceRepositoryImpl;
import com.socialNetwork.socialNetwork.repository.interfacePackage.ResourceRepository;
import com.socialNetwork.socialNetwork.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    ResourceRepositoryImpl resourceRepositoryImpl;

    @Override
    public String UploadeFile(MultipartFile file) {
        try{
            Resource resource = Resource.builder()
                    .name(file.getName())
                    .type(file.getContentType())
                    .data(file.getBytes())
                    .build();
            resourceRepositoryImpl.save(resource);
            return Arrays.toString(file.getBytes());
        } catch (Exception e) {
            log.info("Error: {}", e);
        }
        return null;
    }
}
