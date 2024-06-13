package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.LabReportShare;
import io.bootify.health_hive.domain.User;
import io.bootify.health_hive.model.LabReportShareDTO;
import io.bootify.health_hive.repos.LabReportShareRepository;
import io.bootify.health_hive.repos.UserRepository;
import io.bootify.health_hive.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class LabReportShareService {

    private final LabReportShareRepository labReportShareRepository;
    private final UserRepository userRepository;

    public LabReportShareService(final LabReportShareRepository labReportShareRepository,
            final UserRepository userRepository) {
        this.labReportShareRepository = labReportShareRepository;
        this.userRepository = userRepository;
    }
//need to remove this method(findall)
    public List<LabReportShareDTO> findAll() {
        final List<LabReportShare> labReportShares = labReportShareRepository.findAll(Sort.by("id"));
        return labReportShares.stream()
                .map(labReportShare -> mapToDTO(labReportShare, new LabReportShareDTO()))
                .toList();
    }
    public List<LabReportShareDTO> findAllByDoctor(Long id) {
        final List<LabReportShare> labReportShares = labReportShareRepository.findAllByDoctor(userRepository.findById(id).get());
        return labReportShares.stream()
                .map(labReportShare -> mapToDTO(labReportShare, new LabReportShareDTO()))
                .toList();
    }

    public byte [] getReportFile(LabReportShareDTO labReportShareDTO){
        return null;
    }

    public long shareFileUpload(LabReportShareDTO labReportShareDTO, MultipartFile file){
        return 0;
    }

    public LabReportShareDTO get(final Long id) {
        return labReportShareRepository.findById(id)
                .map(labReportShare -> mapToDTO(labReportShare, new LabReportShareDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LabReportShareDTO labReportShareDTO) {
        final LabReportShare labReportShare = new LabReportShare();
        mapToEntity(labReportShareDTO, labReportShare);
        return labReportShareRepository.save(labReportShare).getId();
    }

    public void update(final Long id, final LabReportShareDTO labReportShareDTO) {
        final LabReportShare labReportShare = labReportShareRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(labReportShareDTO, labReportShare);
        labReportShareRepository.save(labReportShare);
    }

    public void delete(final Long id) {
        labReportShareRepository.deleteById(id);
    }

    private LabReportShareDTO mapToDTO(final LabReportShare labReportShare,
                                       final LabReportShareDTO labReportShareDTO) {
        labReportShareDTO.setId(labReportShare.getId());
        labReportShareDTO.setDescription(labReportShare.getDescription());
        labReportShareDTO.setPatientName(labReportShare.getPatientName());
        labReportShareDTO.setPatient(labReportShare.getPatient() == null ? null : labReportShare.getPatient().getId());
        labReportShareDTO.setDoctor(labReportShare.getDoctor() == null ? null : labReportShare.getDoctor().getId());
        return labReportShareDTO;
    }

    private LabReportShare mapToEntity(final LabReportShareDTO labReportShareDTO,
            final LabReportShare labReportShare) {
        labReportShare.setDescription(labReportShareDTO.getDescription());
        labReportShare.setPatientName(labReportShareDTO.getPatientName());
        final User patient = labReportShareDTO.getPatient() == null ? null : userRepository.findById(labReportShareDTO.getPatient())
                .orElseThrow(() -> new NotFoundException("patient not found"));
        labReportShare.setPatient(patient);
        final User doctor = labReportShareDTO.getDoctor() == null ? null : userRepository.findById(labReportShareDTO.getDoctor())
                .orElseThrow(() -> new NotFoundException("doctor not found"));
        labReportShare.setDoctor(doctor);
        return labReportShare;
    }

    public boolean patientExists(final Long id) {
        return labReportShareRepository.existsByPatientId(id);
    }

    public boolean doctorExists(final Long id) {
        return labReportShareRepository.existsByDoctorId(id);
    }

}
