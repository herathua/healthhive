package io.bootify.health_hive.service;

import io.bootify.health_hive.config.PinataClient;
import io.bootify.health_hive.repos.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class IPFSService implements FileServiceImpl {

    @Autowired
    private PinataClient pinataClient;

    @Override
    public String saveFile(MultipartFile file) {
        try {
            // Convert MultipartFile to File
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            // Use PinataClient to pin the file
            String hash = pinataClient.pinFileToIPFS(convFile);
            System.out.println("IPFS Hash: " + hash);

            // Delete the temporary file
            convFile.delete();

            return hash;
        } catch (IOException ex) {
            throw new RuntimeException("Error whilst communicating with Pinata Cloud", ex);
        }
    }

    @Override
    public byte[] loadFile(String hash) {
        throw new UnsupportedOperationException("Retrieving files from IPFS via Pinata is not implemented");
    }
}
