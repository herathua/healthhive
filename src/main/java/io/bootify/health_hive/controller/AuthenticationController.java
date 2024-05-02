package io.bootify.health_hive.controller;

import io.bootify.health_hive.model.UserLoginDTO;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        System.out.println("responce to login");
        // Keycloak details
        String tokenUrl = "http://keycloak-hh:8080/realms/myrealm/protocol/openid-connect/token";
        String clientId = "myclient";
        String clientSecret = "Uf5UoLWaLEYgK5YS8ERNtorhl9xtEQvd";

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "password");
        body.add("username", userLoginDTO.getEmail());
        body.add("password", userLoginDTO.getPassword());

        // Entity
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, String.class);
        System.out.println("keyclock responce to login");
        // Return the token (you might want to map the response to a JSON object or directly return the JSON string)
        return ResponseEntity.ok(response.getBody());
    }
}
