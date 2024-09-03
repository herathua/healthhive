package io.bootify.health_hive.service;

import io.bootify.health_hive.model.LabDTO;
import io.bootify.health_hive.model.UserDTO;
import jakarta.ws.rs.WebApplicationException;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class KeycloakService {

    private static final String SERVER_URL = "https://lemur-14.cloud-iam.com/auth";
    private static final String REALM = "teamnovauom";
    private static final String CLIENT_ID = "Health-Hive-Client";
    private static final String ADMIN_USERNAME = "teamnova.uom@gmail.com";
    private static final String ADMIN_PASSWORD = "12345";

    public static void updateLabInKeycloak(LabDTO labDTO) {
        System.out.println("Lab updated successfully");
    }


    private Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .grantType(OAuth2Constants.PASSWORD)
                .username(ADMIN_USERNAME)
                .password(ADMIN_PASSWORD)
                .build();
    }

    public String addUser(UserDTO userDTO) {
        Keycloak keycloak;
        try{
             keycloak = keycloak();
            System.out.println("build success");
        }catch (Exception e){
            log.error("Failed to build: {}", userDTO.getEmail(), e);
            throw new RuntimeException("Failed to create user", e);}

        try {
            UserRepresentation user = new UserRepresentation();
            System.out.println("user rep success");
            user.setUsername(userDTO.getEmail());
            System.out.println("user name success");
            user.setFirstName(userDTO.getFullName());
            user.setEmail(userDTO.getEmail());
            user.setEnabled(true);
            System.out.println("user enabled success");

            Response response = keycloak.realm(REALM).users().create(user);
            System.out.println("response success");
            log.info("Create User Response: {}", response.getStatusInfo().toString());

            if (response.getStatus() != 201) {
                log.error("Failed to create user: {}", response.getStatusInfo());
                return null;
            }
            String userId = CreatedResponseUtil.getCreatedId(response);
            try {

                System.out.println("User ID: " + userId);
                UserResource userResource = keycloak.realm(REALM).users().get(userId);

                userResource.executeActionsEmail(Arrays.asList("UPDATE_PASSWORD", "VERIFY_EMAIL"));

            } catch (Exception e) {
                log.error("Failed to send email to user: {}", userDTO.getEmail(), e);
                throw new RuntimeException("Failed to send email to user", e);
            }
            String userGroupId = "25a5e7fe-3eb3-4257-a08c-4200bdaa1f7d";

            try {
                keycloak.realm(REALM).users().get(userId).joinGroup(userGroupId);
            }catch (Exception e) {
                log.error("Failed to add user to group: {}", userGroupId, e);
            }
            return String.format("User created successfully: %s", userDTO.getEmail());    }
        catch (WebApplicationException e) {
            log.error("Failed to create user. Status: {}, Message: {}", e.getResponse().getStatus(), e.getMessage());
            throw new RuntimeException("Failed to create user", e);
        }
        finally {
            keycloak.close();
        }
    }

    public String addLab(LabDTO labDTO) {
        Keycloak keycloak = keycloak();

        try {
            UserRepresentation user = new UserRepresentation();
            user.setUsername(labDTO.getEmail());
            user.setFirstName(labDTO.getLabName());
            user.setEmail(labDTO.getEmail());
            user.setEnabled(true);

            Response response = keycloak.realm(REALM).users().create(user);

            if (response.getStatus() != 201) {
                log.error("Failed to create user: {}", response.getStatusInfo());
                return null;
            }

            String labId = CreatedResponseUtil.getCreatedId(response);

            try {

                System.out.println("User ID: " + labId);
                UserResource userResource = keycloak.realm(REALM).users().get(labId);

                userResource.executeActionsEmail(Arrays.asList("UPDATE_PASSWORD", "VERIFY_EMAIL"));

            } catch (Exception e) {
                log.error("Failed to send email to user: {}", labDTO.getEmail(), e);
                throw new RuntimeException("Failed to send email to user", e);
            }

            String labGroupId = "0803924f-18bb-414a-8aaf-b8c154cc197a";

            try {
                keycloak.realm(REALM).users().get(labId).joinGroup(labGroupId);
            }catch (Exception e) {
                log.error("Failed to add user to group: {}", labGroupId, e);
                throw new RuntimeException("Failed to add user to group", e);
            }

            log.info("User created successfully: {},", labDTO.getEmail());
            return String.format("User created successfully: %s", labDTO.getEmail());
        }
        catch (Exception e) {
            log.error("Failed to create user: {}", labDTO.getEmail(), e);
            throw new RuntimeException("Failed to create user", e);
        }
        finally{
            keycloak.close();
        }
    }





    public boolean deleteEntityKeycloak(String userEmail) {
        if (userEmail == null || userEmail.isEmpty()) {
            log.error("User email must not be null or empty.");
            throw new IllegalArgumentException("User email must not be null or empty.");
        }

        Keycloak keycloak = keycloak();
        try {
            List<UserRepresentation> users = keycloak.realm(REALM).users().search(userEmail);

            if (users != null && !users.isEmpty()) {
                System.out.println("User ID: ");
                String userId = users.get(0).getId();

                keycloak.realm(REALM).users().get(userId).remove();
                log.info("Lab user deleted successfully: {}", userEmail);
                return true;
            } else {
                log.warn("Lab user with email {} not found.", userEmail);
            }
        } catch (Exception e) {
            log.error("Failed to delete lab user: {}", userEmail, e);
            throw new RuntimeException("Failed to delete lab user", e);
        } finally {
            keycloak.close();
        }
        return false;
    }
}