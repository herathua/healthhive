package io.bootify.health_hive.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileServiceImpl {


    String saveFile(MultipartFile file);

    byte[] loadFile(String hash);
}
