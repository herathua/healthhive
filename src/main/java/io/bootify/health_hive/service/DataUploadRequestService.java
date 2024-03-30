package io.bootify.health_hivw.service;

import io.bootify.health_hivw.domain.DataUploadReqeust;
import io.bootify.health_hivw.domain.File;
import io.bootify.health_hivw.domain.User;
import io.bootify.health_hivw.model.DataUploadReqeustDTO;
import io.bootify.health_hivw.repos.DataUploadReqeustRepository;
import io.bootify.health_hivw.repos.FileRepository;
import io.bootify.health_hivw.repos.UserRepository;
import io.bootify.health_hivw.util.NotFoundException;
import io.bootify.health_hivw.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class DataUploadRequestService {

    private final DataUploadReqeustRepository dataUploadReqeustRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final IPFSService ipfsService;

    public DataUploadRequestService(final DataUploadReqeustRepository dataUploadReqeustRepository,
                                    final UserRepository userRepository, final FileRepository fileRepository, final IPFSService ipfsService) {
        this.dataUploadReqeustRepository = dataUploadReqeustRepository;
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.ipfsService = ipfsService;
    }

    public List<DataUploadReqeustDTO> findAll() {
        final List<DataUploadReqeust> dataUploadReqeusts = dataUploadReqeustRepository.findAll(Sort.by("id"));
        return dataUploadReqeusts.stream()
                .map(dataUploadReqeust -> mapToDTO(dataUploadReqeust, new DataUploadReqeustDTO()))
                .toList();
    }

    public DataUploadReqeustDTO get(final Long id) {
        return dataUploadReqeustRepository.findById(id)
                .map(dataUploadReqeust -> mapToDTO(dataUploadReqeust, new DataUploadReqeustDTO()))
                .orElseThrow(NotFoundException::new);

    }

    public byte[] getFileFromIPFS(String hash) {
        return ipfsService.getFileFromIPFS(hash);
    }


    public Long create(final DataUploadReqeustDTO dataUploadReqeustDTO, MultipartFile file) {
        final DataUploadReqeust dataUploadReqeust = new DataUploadReqeust();
        mapToEntity(dataUploadReqeustDTO, dataUploadReqeust);
        ipfsService.saveFileToIPFS(file.getOriginalFilename(), file);
        return dataUploadReqeustRepository.save(dataUploadReqeust).getId();
    }

    public void update(final Long id, final DataUploadReqeustDTO dataUploadReqeustDTO) {
        final DataUploadReqeust dataUploadReqeust = dataUploadReqeustRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dataUploadReqeustDTO, dataUploadReqeust);
        dataUploadReqeustRepository.save(dataUploadReqeust);
    }

    public void delete(final Long id) {
        dataUploadReqeustRepository.deleteById(id);
    }

    private DataUploadReqeustDTO mapToDTO(final DataUploadReqeust dataUploadReqeust,
            final DataUploadReqeustDTO dataUploadReqeustDTO) {
        dataUploadReqeustDTO.setId(dataUploadReqeust.getId());
        dataUploadReqeustDTO.setUser(dataUploadReqeust.getUser() == null ? null : dataUploadReqeust.getUser().getId());
        return dataUploadReqeustDTO;
    }


    private DataUploadReqeust mapToEntity(final DataUploadReqeustDTO dataUploadReqeustDTO,
            final DataUploadReqeust dataUploadReqeust) {
        final User user = dataUploadReqeustDTO.getUser() == null ? null : userRepository.findById(dataUploadReqeustDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        dataUploadReqeust.setUser(user);
        return dataUploadReqeust;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final DataUploadReqeust dataUploadReqeust = dataUploadReqeustRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final File dataUploadReqeustFile = fileRepository.findFirstByDataUploadReqeust(dataUploadReqeust);
        if (dataUploadReqeustFile != null) {
            referencedWarning.setKey("dataUploadReqeust.file.dataUploadReqeust.referenced");
            referencedWarning.addParam(dataUploadReqeustFile.getId());
            return referencedWarning;
        }
        return null;
    }

}
