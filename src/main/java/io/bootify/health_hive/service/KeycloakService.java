package io.bootify.health_hive.service;

import io.bootify.health_hive.model.LabDTO;
import io.bootify.health_hive.model.UserDTO;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class KeycloakService {

    private static final String SERVER_URL = "http://localhost:8080";
    private static final String REALM = "master";
    private static final String CLIENT_ID = "admin-cli";
    private static final String CLIENT_SECRET = "FXMsXGV3fPjmgRCQ3P56A6opOoCX9Xyn";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    // Hardcoded group IDs
    private static final String GROUP1_ID = "b0eee14a-5b16-4e19-b943-d0d6574722ee";
    private static final String GROUP2_ID = "fd64c7ab-f16a-4056-a56b-ab318978af32";

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

        // Add user to group 1
        keycloak.realm(REALM).users().get(userId).joinGroup(GROUP1_ID);

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

        // Add user to group 2
        keycloak.realm(REALM).users().get(userId).joinGroup(GROUP2_ID);

        keycloak.close();

        log.info("User created successfully: {}, Temporary password: {}", labDTO.getEmail(), temporaryPassword);
        return temporaryPassword;
    }

    public boolean deleteLabInKeycloak(String userEmail) {
        if (userEmail == null || userEmail.isEmpty()) {
            log.error("User email must not be null or empty.");
            throw new IllegalArgumentException("User email must not be null or empty.");
        }

        Keycloak keycloak = keycloak();
        try {
            // Search for the user by email
            List<UserRepresentation> users = keycloak.realm(REALM).users().search(userEmail);

            if (users != null && !users.isEmpty()) {
                // Get the user ID
                String userId = users.get(0).getId();

                // Delete the user by ID
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
        user.setLastName(userDTO.getFullName());
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

        // Add user to group 1
        keycloak.realm(REALM).users().get(userId).joinGroup(GROUP1_ID);

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

    public void deleteUserInKeycloak(String email) {
        Keycloak keycloak = keycloak();
        try {
            // Search for the user by email
            List<UserRepresentation> users = keycloak.realm(REALM).users().search(email);

            if (users != null && !users.isEmpty()) {
                // Get the user ID
                String userId = users.get(0).getId();

                // Delete the user by ID
                Response response = keycloak.realm(REALM).users().delete(userId);
                if (response.getStatus() == 204) {
                    log.info("User deleted successfully: {}", email);
                } else {
                    log.warn("Failed to delete user. Response status: {}", response.getStatus());
                }
            } else {
                log.warn("User with email {} not found.", email);
            }
        } catch (Exception e) {
            log.error("Failed to delete user: {}", email, e);
            throw new RuntimeException("Failed to delete user", e);
        } finally {
            keycloak.close();
        }
    }

    // New method to edit user details
    public boolean editUserDetails(UserDTO userDTO) {
        Keycloak keycloak = keycloak();
        try {
            List<UserRepresentation> users = keycloak.realm(REALM).users().search(userDTO.getEmail());
            if (users != null && !users.isEmpty()) {
                UserRepresentation user = users.get(0);
                user.setFirstName(userDTO.getFullName());
                user.setEmail(userDTO.getEmail());
                keycloak.realm(REALM).users().get(user.getId()).update(user);
                log.info("User details updated successfully: {}", userDTO.getEmail());
                return true;
            } else {
                log.warn("User with email {} not found.", userDTO.getEmail());
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to update user details: {}", userDTO.getEmail(), e);
            throw new RuntimeException("Failed to update user details", e);
        } finally {
            keycloak.close();
        }
    }

    // New method to edit lab details
    public boolean editLabDetails(LabDTO labDTO) {
        Keycloak keycloak = keycloak();
        try {
            List<UserRepresentation> users = keycloak.realm(REALM).users().search(labDTO.getEmail());
            if (users != null && !users.isEmpty()) {
                UserRepresentation user = users.get(0);
                user.setFirstName(labDTO.getLabName());
                user.setEmail(labDTO.getEmail());
                keycloak.realm(REALM).users().get(user.getId()).update(user);
                log.info("Lab details updated successfully: {}", labDTO.getEmail());
                return true;
            } else {
                log.warn("Lab user with email {} not found.", labDTO.getEmail());
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to update lab details: {}", labDTO.getEmail(), e);
            throw new RuntimeException("Failed to update lab details", e);
        } finally {
            keycloak.close();
        }
    }
}
