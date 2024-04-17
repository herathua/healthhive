package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.DataUploadRequest;
import io.bootify.health_hive.domain.File;
import io.bootify.health_hive.domain.User;
import io.bootify.health_hive.model.DataUploadRequestDTO;
import io.bootify.health_hive.repos.DataUploadRequestRepository;
import io.bootify.health_hive.repos.FileRepository;
import io.bootify.health_hive.repos.UserRepository;
import io.bootify.health_hive.util.NotFoundException;
import io.bootify.health_hive.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class DataUploadRequestService {

    private final DataUploadRequestRepository dataUploadRequestRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final IPFSService ipfsService;

    public DataUploadRequestService(final DataUploadRequestRepository dataUploadRequestRepository,
                                    final UserRepository userRepository, final FileRepository fileRepository, final IPFSService ipfsService) {
        this.dataUploadRequestRepository = dataUploadRequestRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.ipfsService = ipfsService;
    }

    public List<DataUploadRequestDTO> findAll() {
        final List<DataUploadRequest> dataUploadRequests = dataUploadRequestRepository.findAll(Sort.by("id"));
        return dataUploadRequests.stream()
                .map(dataUploadRequest -> mapToDTO(dataUploadRequest, new DataUploadRequestDTO()))
                .toList();
    }

    public DataUploadRequestDTO get(final Long id) {
        return dataUploadRequestRepository.findById(id)
                .map(dataUploadRequest -> mapToDTO(dataUploadRequest, new DataUploadRequestDTO()))
                .orElseThrow(NotFoundException::new);

    }

//    public byte[] getFileFromIPFS(String hash) {
//        return ipfsService.getFileFromIPFS(hash);
//    }


    public Long create(final DataUploadRequestDTO dataUploadRequestDTO, MultipartFile file) {
        final DataUploadRequest dataUploadRequest = new DataUploadRequest();
        mapToEntity(dataUploadRequestDTO, dataUploadRequest);
        ipfsService.saveFile(file);
        return dataUploadRequestRepository.save(dataUploadRequest).getId();
    }

    public void update(final Long id, final DataUploadRequestDTO dataUploadRequestDTO) {
        final DataUploadRequest dataUploadRequest = dataUploadRequestRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dataUploadRequestDTO, dataUploadRequest);
        dataUploadRequestRepository.save(dataUploadRequest);
    }

    public void delete(final Long id) {
        dataUploadRequestRepository.deleteById(id);
    }

    private DataUploadRequestDTO mapToDTO(final DataUploadRequest dataUploadRequest,
                                          final DataUploadRequestDTO dataUploadRequestDTO) {
        dataUploadRequestDTO.setId(dataUploadRequest.getId());
        dataUploadRequestDTO.setUser(dataUploadRequest.getUser() == null ? null : dataUploadRequest.getUser().getId());
        return dataUploadRequestDTO;
    }


    private DataUploadRequest mapToEntity(final DataUploadRequestDTO dataUploadRequestDTO,
                                          final DataUploadRequest dataUploadRequest) {
        final User user = dataUploadRequestDTO.getUser() == null ? null : userRepository.findById(dataUploadRequestDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        dataUploadRequest.setUser(user);
        return dataUploadRequest;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final DataUploadRequest dataUploadRequest = dataUploadRequestRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final File dataUploadReqeustFile = fileRepository.findFirstByDataUploadRequest(dataUploadRequest);
        if (dataUploadReqeustFile != null) {
            referencedWarning.setKey("dataUploadRequest.file.dataUploadRequest.referenced");
            referencedWarning.addParam(dataUploadReqeustFile.getId());
            return referencedWarning;
        }
        return null;
    }

}
