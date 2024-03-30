package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.File;
import io.bootify.health_hive.domain.LabDataUpload;
import io.bootify.health_hive.domain.LabRequest;
import io.bootify.health_hive.model.LabDataUploadDTO;
import io.bootify.health_hive.repos.FileRepository;
import io.bootify.health_hive.repos.LabDataUploadRepository;
import io.bootify.health_hive.repos.LabRequestRepository;
import io.bootify.health_hive.util.NotFoundException;
import io.bootify.health_hive.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class LabDataUploadService {

    private final LabDataUploadRepository labDataUploadRepository;
    private final LabRequestRepository labRequestRepository;
    private final FileRepository fileRepository;


    public LabDataUploadService(final LabDataUploadRepository labDataUploadRepository,
            final LabRequestRepository labRequestRepository, final FileRepository fileRepository) {
        this.labDataUploadRepository = labDataUploadRepository;
        this.labRequestRepository = labRequestRepository;
        this.fileRepository = fileRepository;

    }

    public List<LabDataUploadDTO> findAll() {
        final List<LabDataUpload> labDataUploads = labDataUploadRepository.findAll(Sort.by("id"));
        return labDataUploads.stream()
                .map(labDataUpload -> mapToDTO(labDataUpload, new LabDataUploadDTO()))
                .toList();
    }

    public LabDataUploadDTO get(final Long id) {
        return labDataUploadRepository.findById(id)
                .map(labDataUpload -> mapToDTO(labDataUpload, new LabDataUploadDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LabDataUploadDTO labDataUploadDTO) {
        final LabDataUpload labDataUpload = new LabDataUpload();
        mapToEntity(labDataUploadDTO, labDataUpload);
        return labDataUploadRepository.save(labDataUpload).getId();
    }

    public void update(final Long id, final LabDataUploadDTO labDataUploadDTO) {
        final LabDataUpload labDataUpload = labDataUploadRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(labDataUploadDTO, labDataUpload);
        labDataUploadRepository.save(labDataUpload);
    }
    public void uploadFile (LabDataUploadDTO labDataUploadDTO, MultipartFile file){
        final LabDataUpload labDataUpload = new LabDataUpload();
        mapToEntity(labDataUploadDTO, labDataUpload);

        labDataUploadRepository.save(labDataUpload);
    }
    public  LabDataUploadDTO searchByLabRequest (Long labRequestId){
        final LabDataUpload labDataUpload = labDataUploadRepository.findFirstByLabRequest(labRequestRepository.findById(labRequestId).orElseThrow(NotFoundException::new));
        return mapToDTO(labDataUpload, new LabDataUploadDTO());
    }

    public byte[] getFileFromDatabase(LabDataUploadDTO labDataUploadDTO) {
        return null;
    }

    public void delete(final Long id) {
        labDataUploadRepository.deleteById(id);
    }

    private LabDataUploadDTO mapToDTO(final LabDataUpload labDataUpload,
            final LabDataUploadDTO labDataUploadDTO) {
        labDataUploadDTO.setId(labDataUpload.getId());
        labDataUploadDTO.setDescription(labDataUpload.getDescription());
        labDataUploadDTO.setLabRequest(labDataUpload.getLabRequest() == null ? null : labDataUpload.getLabRequest().getId());
        return labDataUploadDTO;
    }

    private LabDataUpload mapToEntity(final LabDataUploadDTO labDataUploadDTO,
            final LabDataUpload labDataUpload) {
        labDataUpload.setDescription(labDataUploadDTO.getDescription());
        final LabRequest labRequest = labDataUploadDTO.getLabRequest() == null ? null : labRequestRepository.findById(labDataUploadDTO.getLabRequest())
                .orElseThrow(() -> new NotFoundException("labRequest not found"));
        labDataUpload.setLabRequest(labRequest);
        return labDataUpload;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final LabDataUpload labDataUpload = labDataUploadRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final File labDataUploadFile = fileRepository.findFirstByLabDataUpload(labDataUpload);
        if (labDataUploadFile != null) {
            referencedWarning.setKey("labDataUpload.file.labDataUpload.referenced");
            referencedWarning.addParam(labDataUploadFile.getId());
            return referencedWarning;
        }
        return null;
    }

}
