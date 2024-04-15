package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.DataUploadRequest;
import io.bootify.health_hive.domain.File;
import io.bootify.health_hive.domain.LabDataUpload;
import io.bootify.health_hive.model.FileDTO;
import io.bootify.health_hive.repos.DataUploadRequestRepository;
import io.bootify.health_hive.repos.FileRepository;
import io.bootify.health_hive.repos.LabDataUploadRepository;
import io.bootify.health_hive.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FileService {

    private final FileRepository fileRepository;
    private final LabDataUploadRepository labDataUploadRepository;
    private final DataUploadRequestRepository dataUploadRequestRepository;

    public FileService(final FileRepository fileRepository,
                       final LabDataUploadRepository labDataUploadRepository,
                       final DataUploadRequestRepository dataUploadRequestRepository) {
        this.fileRepository = fileRepository;
        this.labDataUploadRepository = labDataUploadRepository;
        this.dataUploadRequestRepository = dataUploadRequestRepository;
    }

    public List<FileDTO> findAll() {
        final List<File> files = fileRepository.findAll(Sort.by("id"));
        return files.stream()
                .map(file -> mapToDTO(file, new FileDTO()))
                .toList();
    }

    public FileDTO get(final Long id) {
        return fileRepository.findById(id)
                .map(file -> mapToDTO(file, new FileDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FileDTO fileDTO) {
        final File file = new File();
        mapToEntity(fileDTO, file);
        return fileRepository.save(file).getId();
    }

    public void update(final Long id, final FileDTO fileDTO) {
        final File file = fileRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(fileDTO, file);
        fileRepository.save(file);
    }

    public void delete(final Long id) {
        fileRepository.deleteById(id);
    }

    private FileDTO mapToDTO(final File file, final FileDTO fileDTO) {
        fileDTO.setId(file.getId());
        fileDTO.setName(file.getName());
        fileDTO.setType(file.getType());
        fileDTO.setFilePath(file.getFilePath());
        fileDTO.setCreatedDate(file.getCreatedDate());
        fileDTO.setLabDataUpload(file.getLabDataUpload() == null ? null : file.getLabDataUpload().getId());
        fileDTO.setDataUploadReqeust(file.getDataUploadRequest() == null ? null : file.getDataUploadRequest().getId());
        return fileDTO;
    }

    private File mapToEntity(final FileDTO fileDTO, final File file) {
        file.setName(fileDTO.getName());
        file.setType(fileDTO.getType());
        file.setFilePath(fileDTO.getFilePath());
        file.setCreatedDate(fileDTO.getCreatedDate());
        final LabDataUpload labDataUpload = fileDTO.getLabDataUpload() == null ? null : labDataUploadRepository.findById(fileDTO.getLabDataUpload())
                .orElseThrow(() -> new NotFoundException("labDataUpload not found"));
        file.setLabDataUpload(labDataUpload);
        final DataUploadRequest dataUploadReqeust = fileDTO.getDataUploadRequest() == null ? null : dataUploadRequestRepository.findById(fileDTO.getDataUploadRequest())
                .orElseThrow(() -> new NotFoundException("dataUploadReqeust not found"));
        file.setDataUploadRequest(dataUploadReqeust);
        return file;
    }

}
