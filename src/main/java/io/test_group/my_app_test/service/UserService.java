package io.test_group.my_app_test.service;

import io.test_group.my_app_test.domain.User;
import io.test_group.my_app_test.model.Gender;
import io.test_group.my_app_test.model.UserDTO;
import io.test_group.my_app_test.repos.UserRepository;
import io.test_group.my_app_test.util.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        /*
        UserDTO obj1 = new UserDTO();
        obj1.setName("Chamika");
        obj1.setEmail("chamika123@gmail.com");
        obj1.setMobileNumber("0711234567");
        obj1.setAddress("Nuwara Eliya Road,Kandy");
        obj1.setDateOfBIrth(LocalDate.now());
        obj1.setBirthCertificateNumber("1234");
        obj1.setNic("12345678910V");
        obj1.setEmergencyContactName("Eranda");
        obj1.setEmergencyContactNumber("0701234567");
        obj1.setGender(Gender.MALE);

        create(obj1);*/
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setMobileNumber(user.getMobileNumber());
        userDTO.setAddress(user.getAddress());
        userDTO.setDateOfBIrth(user.getDateOfBIrth());
        userDTO.setBirthCertificateNumber(user.getBirthCertificateNumber());
        userDTO.setNic(user.getNic());
        userDTO.setEmergencyContactName(user.getEmergencyContactName());
        userDTO.setEmergencyContactNumber(user.getEmergencyContactNumber());
        userDTO.setGender(user.getGender());
        userDTO.setChildren(user.getChildren() == null ? null : user.getChildren().getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setAddress(userDTO.getAddress());
        user.setDateOfBIrth(userDTO.getDateOfBIrth());
        user.setBirthCertificateNumber(userDTO.getBirthCertificateNumber());
        user.setNic(userDTO.getNic());
        user.setEmergencyContactName(userDTO.getEmergencyContactName());
        user.setEmergencyContactNumber(userDTO.getEmergencyContactNumber());
        user.setGender(userDTO.getGender());
        final User children = userDTO.getChildren() == null ? null : userRepository.findById(userDTO.getChildren())
                .orElseThrow(() -> new NotFoundException("children not found"));
        user.setChildren(children);
        return user;
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

}
