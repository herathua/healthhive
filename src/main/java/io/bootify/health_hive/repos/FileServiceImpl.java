package io.bootify.health_hive.repos;

import org.springframework.web.multipart.MultipartFile;

public interface FileServiceImpl {

    String saveFileToIPFS(String name,MultipartFile file);

    byte[] getFileFromIPFS(String hash);

    //byte[] getFileFromIPFS(String hash);
}
