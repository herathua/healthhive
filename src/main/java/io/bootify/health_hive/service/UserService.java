package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.DataUploadReqeust;
import io.bootify.health_hive.domain.LabReportShare;
import io.bootify.health_hive.domain.LabRequest;
import io.bootify.health_hive.domain.User;
import io.bootify.health_hive.model.UserDTO;
import io.bootify.health_hive.repos.DataUploadReqeustRepository;
import io.bootify.health_hive.repos.LabReportShareRepository;
import io.bootify.health_hive.repos.LabRequestRepository;
import io.bootify.health_hive.repos.UserRepository;
import io.bootify.health_hive.util.NotFoundException;
import io.bootify.health_hive.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final KeycloackService keycloackService;

    private final UserRepository userRepository;
    private final LabRequestRepository labRequestRepository;
    private final DataUploadReqeustRepository dataUploadReqeustRepository;
    private final LabReportShareRepository labReportShareRepository;

    public UserService(final UserRepository userRepository,
            final LabRequestRepository labRequestRepository,
            final DataUploadReqeustRepository dataUploadReqeustRepository,
            final LabReportShareRepository labReportShareRepository ,KeycloackService keycloackService) {
        this.userRepository = userRepository;
        this.labRequestRepository = labRequestRepository;
        this.dataUploadReqeustRepository = dataUploadReqeustRepository;
        this.labReportShareRepository = labReportShareRepository;
        this.keycloackService = keycloackService;
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
        keycloackService.createUserInKeycloak(userDTO);
        return userRepository.save(user).getId();

    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        keycloackService.updateUserInKeycloak(userDTO);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
        keycloackService.deleteUserInKeycloak(get(id));
    }

    public void resetPassword(final Long id, final String tempPassword) {
        final UserDTO userDTO = get(id);
        keycloackService.resetPassword(userDTO, tempPassword);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setGender(user.getGender());
        userDTO.setAge(user.getAge());
        userDTO.setEmail(user.getEmail());
        userDTO.setTelephoneNumber(user.getTelephoneNumber());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFullName(userDTO.getFullName());
        user.setGender(userDTO.getGender());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setTelephoneNumber(userDTO.getTelephoneNumber());
        return user;
    }

    public boolean emailExists(final String email) {
        return userRepository.existsByEmailIgnoreCase(email);
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
        final DataUploadReqeust userDataUploadReqeust = dataUploadReqeustRepository.findFirstByUser(user);
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
