package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.LabReportShare;
import io.bootify.health_hive.domain.ShareFile;
import io.bootify.health_hive.domain.User;
import io.bootify.health_hive.model.LabReportShareDTO;
import io.bootify.health_hive.repos.LabReportShareRepository;
import io.bootify.health_hive.repos.ShareFileRepository;
import io.bootify.health_hive.repos.UserRepository;
import io.bootify.health_hive.util.NotFoundException;
import io.bootify.health_hive.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LabReportShareService {

    private final LabReportShareRepository labReportShareRepository;
    private final UserRepository userRepository;
    private final ShareFileRepository shareFileRepository;

    public LabReportShareService(final LabReportShareRepository labReportShareRepository,
                                 final UserRepository userRepository, final ShareFileRepository shareFileRepository) {
        this.labReportShareRepository = labReportShareRepository;
        this.userRepository = userRepository;
        this.shareFileRepository = shareFileRepository;
    }

    public List<LabReportShareDTO> findAll() {
        final List<LabReportShare> labReportShares = labReportShareRepository.findAll(Sort.by("id"));
        return labReportShares.stream()
                .map(labReportShare -> mapToDTO(labReportShare, new LabReportShareDTO()))
                .toList();
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
        labReportShareDTO.setPatient(labReportShare.getPatient() == null ? null : labReportShare.getPatient().getId());
        labReportShareDTO.setDoctor(labReportShare.getDoctor() == null ? null : labReportShare.getDoctor().getId());
        return labReportShareDTO;
    }

    private LabReportShare mapToEntity(final LabReportShareDTO labReportShareDTO,
                                       final LabReportShare labReportShare) {
        labReportShare.setDescription(labReportShareDTO.getDescription());
        final User patient = labReportShareDTO.getPatient() == null ? null : userRepository.findById(labReportShareDTO.getPatient())
                .orElseThrow(() -> new NotFoundException("patient not found"));
        labReportShare.setPatient(patient);
        final User doctor = labReportShareDTO.getDoctor() == null ? null : userRepository.findById(labReportShareDTO.getDoctor())
                .orElseThrow(() -> new NotFoundException("doctor not found"));
        labReportShare.setDoctor(doctor);
        return labReportShare;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final LabReportShare labReportShare = labReportShareRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final ShareFile labReportShareShareFile = shareFileRepository.findFirstByLabReportShare(labReportShare);
        if (labReportShareShareFile != null) {
            referencedWarning.setKey("labReportShare.shareFile.labReportShare.referenced");
            referencedWarning.addParam(labReportShareShareFile.getId());
            return referencedWarning;
        }
        return null;
    }

}
