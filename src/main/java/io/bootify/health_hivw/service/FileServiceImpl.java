package io.bootify.health_hivw.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileServiceImpl {

    String saveFile(MultipartFile file);

    byte[] loadFile(String hash);
}