package io.bootify.health_hive.config;

import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PinataClient {

    private static final String PINATA_API_KEY = "2989622d0628594d9906";
    private static final String PINATA_SECRET_API_KEY = "034e7d872a0755d32d65b2f94030ab9c2f6c4bb4566c1ea28435248bc08a292a";
    private static final String PINATA_BASE_URL = "https://api.pinata.cloud/";

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public PinataClient() {
        this.httpClient = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String pinFileToIPFS(File file) throws IOException {
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(file, MediaType.parse("application/octet-stream")))
                .build();

        Request request = new Request.Builder()
                .url(PINATA_BASE_URL + "pinning/pinFileToIPFS")
                .addHeader("2989622d0628594d9906", PINATA_API_KEY)
                .addHeader("034e7d872a0755d32d65b2f94030ab9c2f6c4bb4566c1ea28435248bc08a292a", PINATA_SECRET_API_KEY)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Parse the response
            Map<String, Object> result = objectMapper.readValue(response.body().string(), HashMap.class);
            return result.get("IpfsHash").toString();
        }
    }
}
