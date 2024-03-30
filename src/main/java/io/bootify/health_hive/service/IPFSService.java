package io.bootify.health_hive.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class IPFSService {
    public String saveFileToIPFS(String name, MultipartFile file) {
        return null;
    }
    public byte[] getFileFromIPFS(String hash) {
        return null;
    }
}