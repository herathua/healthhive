package io.bootify.health_hive.rest;

import io.bootify.health_hive.domain.User;
import io.bootify.health_hive.model.UserDTO;
import io.bootify.health_hive.model.UserLoginDTO;
import io.bootify.health_hive.repos.UserRepository;
import io.bootify.health_hive.service.KeycloakService;
import io.bootify.health_hive.service.UserService;
import io.bootify.health_hive.util.NotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private final UserService userService;
    private final KeycloakService keycloakService;
    private final UserRepository userRepository;

    public UserResource(final UserService userService, final KeycloakService keycloakService, final UserRepository userRepository) {
        this.userService = userService;
        this.keycloakService = keycloakService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable(name = "email") final String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException();
        }
        return ResponseEntity.ok(userService.get(user.getId()));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createUser(@RequestBody final UserDTO userDTO) {
//        final Long createdId = userService.create(userDTO);
        System.out.println("this is some text");
        final String tempPassword = keycloakService.createUserInKeycloak(userDTO);
        System.out.println("Temporary Password: " + tempPassword);
        return new ResponseEntity<>(tempPassword, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        boolean loginSuccessful = keycloakService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        if (loginSuccessful) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/{id}/reset-password")
    public ResponseEntity<Void> resetUserPassword(@PathVariable Long id, @RequestBody String tempPassword) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        keycloakService.resetUserPassword(userDTO, tempPassword);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateUser(@PathVariable(name = "id") final Long id,
                                           @RequestBody @Valid final UserDTO userDTO) {
        userService.update(id, userDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{email}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "email") final String email) {
        System.out.println("Api called");
        keycloakService.deleteUserInKeycloak((email));
        return null;
    }

}