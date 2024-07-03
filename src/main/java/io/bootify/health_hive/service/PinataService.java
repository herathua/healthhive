package io.bootify.health_hive.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost uploadFile = new HttpPost(PINATA_PIN_FILE_URL);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", file.getInputStream(), ContentType.DEFAULT_BINARY, file.getOriginalFilename());

            uploadFile.setEntity(builder.build());
            uploadFile.addHeader("pinata_api_key", pinataApiKey);
            uploadFile.addHeader("pinata_secret_api_key", pinataSecretKey);
            uploadFile.addHeader("Authorization", "Bearer " + jwtsecret);

            try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                String ipfsHash = extractIpfsHash(jsonResponse);
                return ipfsHash;
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Error uploading file to IPFS", e);
        }
    }

    public byte[] loadFile(String hash) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(PINATA_GET_FILE_URL + hash);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return EntityUtils.toByteArray(response.getEntity());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading file from IPFS", e);
        }
    }

    private String extractIpfsHash(String jsonResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonResponse);
        return rootNode.get("IpfsHash").asText();
    }
}
