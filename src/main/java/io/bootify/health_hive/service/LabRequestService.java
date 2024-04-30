package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.Lab;
import io.bootify.health_hive.domain.LabDataUpload;
import io.bootify.health_hive.domain.LabRequest;
import io.bootify.health_hive.domain.User;
import io.bootify.health_hive.model.LabRequestDTO;
import io.bootify.health_hive.repos.LabDataUploadRepository;
import io.bootify.health_hive.repos.LabRepository;
import io.bootify.health_hive.repos.LabRequestRepository;
import io.bootify.health_hive.repos.UserRepository;
import io.bootify.health_hive.util.NotFoundException;
import io.bootify.health_hive.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LabRequestService {

    private final LabRequestRepository labRequestRepository;
    private final UserRepository userRepository;
    private final LabRepository labRepository;
    private final LabDataUploadRepository labDataUploadRepository;

    public LabRequestService(final LabRequestRepository labRequestRepository,
                             final UserRepository userRepository, final LabRepository labRepository,
                             final LabDataUploadRepository labDataUploadRepository) {
        this.labRequestRepository = labRequestRepository;
        this.userRepository = userRepository;
        this.labRepository = labRepository;
        this.labDataUploadRepository = labDataUploadRepository;
    }

    public List<LabRequestDTO> findAll() {
        final List<LabRequest> labRequests = labRequestRepository.findAll(Sort.by("id"));
        return labRequests.stream()
                .map(labRequest -> mapToDTO(labRequest, new LabRequestDTO()))
                .toList();
    }

    public LabRequestDTO get(final Long id) {
        return labRequestRepository.findById(id)
                .map(labRequest -> mapToDTO(labRequest, new LabRequestDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LabRequestDTO labRequestDTO) {
        final LabRequest labRequest = new LabRequest();
        mapToEntity(labRequestDTO, labRequest);
        return labRequestRepository.save(labRequest).getId();
    }

    public void update(final Long id, final LabRequestDTO labRequestDTO) {
        final LabRequest labRequest = labRequestRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(labRequestDTO, labRequest);
        labRequestRepository.save(labRequest);
    }

    public void delete(final Long id) {
        labRequestRepository.deleteById(id);
    }

    private LabRequestDTO mapToDTO(final LabRequest labRequest, final LabRequestDTO labRequestDTO) {
        labRequestDTO.setId(labRequest.getId());
        labRequestDTO.setDescription(labRequest.getDescription());
        labRequestDTO.setInvoice(labRequest.getInvoice());
        labRequestDTO.setUserEmail(labRequest.getUserEmail() == null ? null : labRequest.getUserEmail().getUserEmail());
        labRequestDTO.setLabEmail(labRequest.getLabEmail() == null ? null : labRequest.getLabEmail().getEmail());
        return labRequestDTO;
    }

    private LabRequest mapToEntity(final LabRequestDTO labRequestDTO, final LabRequest labRequest) {
        labRequest.setDescription(labRequestDTO.getDescription());
        labRequest.setInvoice(labRequestDTO.getInvoice());
        final User userEmail = labRequestDTO.getUserEmail() == null ? null : userRepository.findById(labRequestDTO.getUserEmail())
                .orElseThrow(() -> new NotFoundException("userEmail not found"));
        labRequest.setUserEmail(userEmail);
        final Lab labEmail = labRequestDTO.getLabEmail() == null ? null : labRepository.findById(labRequestDTO.getLabEmail())
                .orElseThrow(() -> new NotFoundException("labEmail not found"));
        labRequest.setLabEmail(labEmail);
        return labRequest;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final LabRequest labRequest = labRequestRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final LabDataUpload labRequestLabDataUpload = labDataUploadRepository.findFirstByLabRequest(labRequest);
        if (labRequestLabDataUpload != null) {
            referencedWarning.setKey("labRequest.labDataUpload.labRequest.referenced");
            referencedWarning.addParam(labRequestLabDataUpload.getId());
            return referencedWarning;
        }
        return null;
    }

}
