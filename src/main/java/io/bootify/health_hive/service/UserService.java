package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.DataUploadRequest;
import io.bootify.health_hive.domain.LabReportShare;
import io.bootify.health_hive.domain.LabRequest;
import io.bootify.health_hive.domain.User;
import io.bootify.health_hive.model.UserDTO;
import io.bootify.health_hive.repos.DataUploadRequestRepository;
import io.bootify.health_hive.repos.LabReportShareRepository;
import io.bootify.health_hive.repos.LabRequestRepository;
import io.bootify.health_hive.repos.UserRepository;
import io.bootify.health_hive.util.NotFoundException;
import io.bootify.health_hive.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.bootify.health_hive.service.KeycloakService;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final LabRequestRepository labRequestRepository;
    private final DataUploadRequestRepository dataUploadRequestRepository;
    private final LabReportShareRepository labReportShareRepository;
    private final KeycloakService keycloakService;

    public UserService(final UserRepository userRepository,
                       final LabRequestRepository labRequestRepository,
                       final DataUploadRequestRepository dataUploadReqeustRepository,
                       final LabReportShareRepository labReportShareRepository,
                       final KeycloakService keycloakService) {
        this.userRepository = userRepository;
        this.labRequestRepository = labRequestRepository;
        this.dataUploadRequestRepository = dataUploadReqeustRepository;
        this.labReportShareRepository = labReportShareRepository;
        this.keycloakService = keycloakService;
    }

    public List<UserDTO> findAll() {
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
//         String msg = keycloackService.createUserInKeycloak(userDTO);
//        System.out.println("\n\nkeycloak message: " + msg + "\n\n");
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

    public ResponseEntity<Void> resetPassword(final Long id, final String tempPassword) {
        final UserDTO userDTO = get(id);
//        keycloakService.resetUserPassword(userDTO, tempPassword);
        return ResponseEntity.ok().build();
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setTelephoneNumber(user.getTelephoneNumber());
        userDTO.setGender(user.getGender());
        userDTO.setAge(user.getAge());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setBirthCertificateNumber(user.getBirthCertificateNumber());
        userDTO.setNic(user.getNic());
        userDTO.setEmergencyContactName(user.getEmergencyContactName());
        userDTO.setEmergencyContactNumber(user.getEmergencyContactNumber());
        userDTO.setProfilePictureUrl(user.getProfilePictureUrl());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setTelephoneNumber(userDTO.getTelephoneNumber());
        user.setGender(userDTO.getGender());
        user.setAge(userDTO.getAge());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setBirthCertificateNumber(userDTO.getBirthCertificateNumber());
        user.setNic(userDTO.getNic());
        user.setEmergencyContactName(userDTO.getEmergencyContactName());
        user.setEmergencyContactNumber(userDTO.getEmergencyContactNumber());
        user.setProfilePictureUrl(userDTO.getProfilePictureUrl());
        return user;
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    public boolean nicExists(final String nic) {
        return userRepository.existsByNicIgnoreCase(nic);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final LabRequest userLabRequest = labRequestRepository.findFirstByUser(user);
        if (userLabRequest != null) {
            referencedWarning.setKey("user.labRequest.user.referenced");
            referencedWarning.addParam(userLabRequest.getId());
            return referencedWarning;
        }
        final DataUploadRequest userDataUploadReqeust = dataUploadRequestRepository.findFirstByUser(user);
        if (userDataUploadReqeust != null) {
            referencedWarning.setKey("user.dataUploadReqeust.user.referenced");
            referencedWarning.addParam(userDataUploadReqeust.getId());
            return referencedWarning;
        }
        final LabReportShare patientLabReportShare = labReportShareRepository.findFirstByPatient(user);
        if (patientLabReportShare != null) {
            referencedWarning.setKey("user.labReportShare.patient.referenced");
            referencedWarning.addParam(patientLabReportShare.getId());
            return referencedWarning;
        }
        final LabReportShare doctorLabReportShare = labReportShareRepository.findFirstByDoctor(user);
        if (doctorLabReportShare != null) {
            referencedWarning.setKey("user.labReportShare.doctor.referenced");
            referencedWarning.addParam(doctorLabReportShare.getId());
            return referencedWarning;
        }
        return null;
    }

}
