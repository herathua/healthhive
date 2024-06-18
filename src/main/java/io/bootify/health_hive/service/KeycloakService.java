package io.bootify.health_hive.service;

import io.bootify.health_hive.model.LabDTO;
import io.bootify.health_hive.model.UserDTO;
import jnr.posix.WString;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeycloakService {

    private static final String SERVER_URL = "http://localhost:8080";
    private static final String REALM = "master";
    private static final String CLIENT_ID = "admin-cli";
    private static final String CLIENT_SECRET = "Us0JlhyNHqGIaFreMiBPOHKw6w0hR3x2";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "123123";

    private Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .grantType(OAuth2Constants.PASSWORD)
                .username(ADMIN_USERNAME)
                .password(ADMIN_PASSWORD)
                .build();
    }

    public boolean login(String username, String password) {
        try {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(SERVER_URL)
                    .realm(REALM)
                    .clientId(CLIENT_ID)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(username)
                    .password(password)
                    .build();
            keycloak.tokenManager().getAccessToken();
            keycloak.close();
            log.info("Login successful for user: {}", username);
            return true;
        } catch (Exception e) {
            log.error("Login failed for user: {}", username, e);
            return false;
        }
    }

    public String addUser(UserDTO userDTO) {
        Keycloak keycloak = keycloak();
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getEmail());
        user.setFirstName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setEnabled(true);

        Response response = keycloak.realm(REALM).users().create(user);
        log.info("Create User Response: {}", response.getStatusInfo().toString());

        if (response.getStatus() != 201) {
            log.error("Failed to create user: {}", response.getStatusInfo());
            return null;
        }

        String userId = CreatedResponseUtil.getCreatedId(response);

        String temporaryPassword = generateTemporaryPassword(userDTO);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(true);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(temporaryPassword);

        keycloak.realm(REALM).users().get(userId).resetPassword(credential);
        keycloak.close();

        log.info("User created successfully: {}, Temporary password: {}", userDTO.getEmail(), temporaryPassword);
        return temporaryPassword;
    }

    private String generateTemporaryPassword(UserDTO userDTO) {
        String preName = userDTO.getFullName().substring(0, Math.min(userDTO.getFullName().length(), 3));
        return preName + userDTO.getBirthCertificateNumber();
    }

    private String generateTemporaryPassword(LabDTO labDTO) {
        String preName = labDTO.getLabName().substring(0, Math.min(labDTO.getLabName().length(), 3));
        return preName + labDTO.getLabRegID();
    }

    public String createLabInKeycloak(LabDTO labDTO) {
        Keycloak keycloak = keycloak();
        UserRepresentation user = new UserRepresentation();
        user.setUsername(labDTO.getEmail());
        user.setFirstName(labDTO.getLabName());
        user.setEmail(labDTO.getEmail());
        user.setEnabled(true);

        Response response = keycloak.realm(REALM).users().create(user);
        log.info("Create User Response: {}", response.getStatusInfo().toString());

        if (response.getStatus() != 201) {
            log.error("Failed to create user: {}", response.getStatusInfo());
            return null;
        }

        String userId = CreatedResponseUtil.getCreatedId(response);

        String temporaryPassword = generateTemporaryPassword(labDTO);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(true);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(temporaryPassword);

        keycloak.realm(REALM).users().get(userId).resetPassword(credential);
        keycloak.close();

        log.info("User created successfully: {}, Temporary password: {}", labDTO.getEmail(), temporaryPassword);
        return temporaryPassword;
    }

    public void deleteLabInKeycloak(LabDTO labDTO) {
        Keycloak keycloak = keycloak();
        try {
            keycloak.realm(REALM).users().delete(labDTO.getId().toString());
            log.info("Lab user deleted successfully: {}", labDTO.getEmail());
        } catch (Exception e) {
            log.error("Failed to delete lab user: {}", labDTO.getEmail(), e);
            throw new RuntimeException("Failed to delete lab user", e);
        } finally {
            keycloak.close();
        }
    }

    public void resetLabPassword(Long id, String tempPassword) {
        Keycloak keycloak = keycloak();
        try {
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setTemporary(true);
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(tempPassword);

            keycloak.realm(REALM).users().get(id.toString()).resetPassword(credential);
            log.info("Temporary password reset successfully for lab user ID: {}", id);
        } catch (Exception e) {
            log.error("Failed to reset password for lab user ID: {}", id, e);
            throw new RuntimeException("Failed to reset password", e);
        } finally {
            keycloak.close();
        }
    }

    public String createUserInKeycloak(UserDTO userDTO) {

        Keycloak keycloak = keycloak();
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getEmail());
        user.setFirstName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setEnabled(true);

        Response response = keycloak.realm(REALM).users().create(user);
        log.info("Create User Response: {}", response.getStatusInfo().toString());

        if (response.getStatus() != 201) {
            log.error("Failed to create user: {}", response.getStatusInfo());
            return null;
        }

        String userId = CreatedResponseUtil.getCreatedId(response);

        String temporaryPassword = generateTemporaryPassword(userDTO);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(true);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(temporaryPassword);

        keycloak.realm(REALM).users().get(userId).resetPassword(credential);
        keycloak.close();

        log.info("User created successfully: {}, Temporary password: {}", userDTO.getEmail(), temporaryPassword);
        return temporaryPassword;
    }

    public void resetUserPassword(UserDTO userDTO, String tempPassword) {
        Keycloak keycloak = keycloak();
        try {
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setTemporary(true);
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(tempPassword);

            keycloak.realm(REALM).users().get(userDTO.getId().toString()).resetPassword(credential);
            log.info("Temporary password reset successfully for user: {}", userDTO.getEmail());
        } catch (Exception e) {
            log.error("Failed to reset password for user: {}", userDTO.getEmail(), e);
            throw new RuntimeException("Failed to reset password", e);
        } finally {
            keycloak.close();
        }
    }
}
