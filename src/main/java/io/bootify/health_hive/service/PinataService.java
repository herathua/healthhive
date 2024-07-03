package io.bootify.health_hive.service;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    private static final String PINATA_PIN_FILE_URL = "https://api.pinata.cloud/pinning/pinFileToIPFS";

    public String pinFileToIPFS(String filePath) throws IOException, ParseException {
        Path path = Paths.get(filePath);
        byte[] fileContent = Files.readAllBytes(path);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(PINATA_PIN_FILE_URL);

        uploadFile.addHeader("pinata_api_key", pinataApiKey);
        uploadFile.addHeader("pinata_secret_api_key", pinataSecretKey);
        uploadFile.addHeader("Content-Type", "multipart/form-data");

        StringEntity entity = new StringEntity(new String(fileContent));
        uploadFile.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(uploadFile);
        String jsonResponse = EntityUtils.toString(response.getEntity());

        response.close();
        httpClient.close();

        return jsonResponse;
    }
}