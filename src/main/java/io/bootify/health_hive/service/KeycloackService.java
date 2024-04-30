package io.bootify.health_hive.service;

import io.bootify.health_hive.model.LabDTO;
import io.bootify.health_hive.model.UserDTO;
import io.bootify.health_hive.model.LabRequestDTO;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.stereotype.Service;
import io.bootify.health_hive.model.UserDTO;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class KeycloackService {
    public String createUserInKeycloak(UserDTO userDTO) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl("http://keycloak-hh:8080")
                .realm("myrealm")
                .grantType(OAuth2Constants.PASSWORD)
                .clientId("myclient")
                .clientSecret("diyAr7E3b9czAlN3kml57jwU1ii14Gfl")
                .username("admin")
                .password("admin")
                .build();

        String preName = userDTO.getFullName().substring(0, Math.min(userDTO.getFullName().length(), 3));
        String password = preName + userDTO.getBirthCertificateNumber();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userDTO.getUserEmail());
        user.setFirstName(userDTO.getFullName());
        user.setEmail(userDTO.getUserEmail());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password); // set user password here
        credential.setTemporary(false);
        user.setCredentials(Arrays.asList(credential));

        // Execute the user creation request
        UsersResource usersResource = keycloak.realm("myrealm").users();
        Response response = usersResource.create(user);

        if (response.getStatus() == 201) {
            // If user creation is successful, extract the userId from the location header
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            return "User created successfully! UserID: " + userId + " with password: " + password;
        } else {
            // If the creation was not successful, read the error message from the response
            String errorMessage = response.readEntity(String.class);
            response.close(); // Close the response to release resources
            return "Error creating user: " + errorMessage;
        }
    }

    public void updateUserInKeycloak(UserDTO UserDTO){

    }
public void deleteUserInKeycloak(UserDTO UserDTO){

    }
    public void resetUserPassword(UserDTO UserDTO, String tempPassword){

    }
    public void createLabInKeycloak(LabDTO LabDTO){

    }

    public static void updateLabInKeycloak(LabDTO LabDTO){

    }
    public void deleteLabInKeycloak(LabDTO id){

    }
    public void resetLabPassword(long id, String tempPassword){

    }


}
