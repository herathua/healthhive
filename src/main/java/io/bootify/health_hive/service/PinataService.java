package io.bootify.health_hive.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PinataService {

    @Value("${pinata.api.key}")
    private String pinataApiKey;

    @Value("${pinata.secret.key}")
    private String pinataSecretKey;

    @Value("${pinata.jwt.secret}")
    private String jwtsecret;

    private static final String PINATA_PIN_FILE_URL = "https://api.pinata.cloud/pinning/pinFileToIPFS";
    private static final String PINATA_GET_FILE_URL = "https://gateway.pinata.cloud/ipfs/";

    public String saveFile(MultipartFile file) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost uploadFile = new HttpPost(PINATA_PIN_FILE_URL);

            uploadFile.addHeader("pinata_api_key", pinataApiKey);
            uploadFile.addHeader("pinata_secret_api_key", pinataSecretKey);
            uploadFile.addHeader("Authorization", "Bearer " + jwtsecret);
            uploadFile.addHeader("Content-Type", "multipart/form-data");

            Path tempFile = Files.createTempFile(file.getOriginalFilename(), null);
            file.transferTo(tempFile);
            byte[] fileContent = Files.readAllBytes(tempFile);

            StringEntity entity = new StringEntity(new String(fileContent));
            uploadFile.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(uploadFile);
            String jsonResponse = EntityUtils.toString(response.getEntity());

            response.close();
            httpClient.close();

            // Parse the response to extract the IPFS hash
            // Assuming jsonResponse is a JSON string that contains a field "IpfsHash"
            // Replace with actual parsing logic
            String ipfsHash = extractIpfsHash(jsonResponse);

            return ipfsHash;
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Error uploading file to IPFS", e);
        }
    }

    public byte[] loadFile(String hash) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet(PINATA_GET_FILE_URL + hash);

            CloseableHttpResponse response = httpClient.execute(request);
            byte[] fileContent = EntityUtils.toByteArray(response.getEntity());

            response.close();
            httpClient.close();

            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("Error loading file from IPFS", e);
        }
    }

    private String extractIpfsHash(String jsonResponse) {
        // Add logic to parse jsonResponse and extract the IpfsHash value
        // This is a placeholder implementation
        return jsonResponse;
    }
}
