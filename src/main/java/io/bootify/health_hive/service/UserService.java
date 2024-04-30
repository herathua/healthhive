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
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final LabRequestRepository labRequestRepository;
    private final DataUploadRequestRepository dataUploadRequestRepository;
    private final LabReportShareRepository labReportShareRepository;

    public UserService(final UserRepository userRepository,
                       final LabRequestRepository labRequestRepository,
                       final DataUploadRequestRepository dataUploadRequestRepository,
                       final LabReportShareRepository labReportShareRepository) {
        this.userRepository = userRepository;
        this.labRequestRepository = labRequestRepository;
        this.dataUploadRequestRepository = dataUploadRequestRepository;
        this.labReportShareRepository = labReportShareRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("userEmail"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final String userEmail) {
        return userRepository.findById(userEmail)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        user.setUserEmail(userDTO.getUserEmail());
        return userRepository.save(user).getUserEmail();
    }

    public void update(final String userEmail, final UserDTO userDTO) {
        final User user = userRepository.findById(userEmail)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final String userEmail) {
        userRepository.deleteById(userEmail);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setUserEmail(user.getUserEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setTelephoneNumber(user.getTelephoneNumber());
        userDTO.setGender(user.getGender());
        userDTO.setAge(user.getAge());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setBirthCertificateNumber(user.getBirthCertificateNumber());
        userDTO.setNic(user.getNic());
        userDTO.setEmergencyContactName(user.getEmergencyContactName());
        userDTO.setEmergencyContactNumber(user.getEmergencyContactNumber());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFullName(userDTO.getFullName());
        user.setTelephoneNumber(userDTO.getTelephoneNumber());
        user.setGender(userDTO.getGender());
        user.setAge(userDTO.getAge());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setBirthCertificateNumber(userDTO.getBirthCertificateNumber());
        user.setNic(userDTO.getNic());
        user.setEmergencyContactName(userDTO.getEmergencyContactName());
        user.setEmergencyContactNumber(userDTO.getEmergencyContactNumber());
        return user;
    }

    public boolean userEmailExists(final String userEmail) {
        return userRepository.existsByUserEmailIgnoreCase(userEmail);
    }

    public boolean nicExists(final String nic) {
        return userRepository.existsByNicIgnoreCase(nic);
    }

    public ReferencedWarning getReferencedWarning(final String userEmail) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(userEmail)
                .orElseThrow(NotFoundException::new);
        final LabRequest userEmailLabRequest = labRequestRepository.findFirstByUserEmail(user);
        if (userEmailLabRequest != null) {
            referencedWarning.setKey("user.labRequest.userEmail.referenced");
            referencedWarning.addParam(userEmailLabRequest.getId());
            return referencedWarning;
        }
        final DataUploadRequest userDataUploadRequest = dataUploadRequestRepository.findFirstByUser(user);
        if (userDataUploadRequest != null) {
            referencedWarning.setKey("user.dataUploadRequest.user.referenced");
            referencedWarning.addParam(userDataUploadRequest.getId());
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
