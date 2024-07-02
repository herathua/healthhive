package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.FileHash;
import io.bootify.health_hive.model.FileHashDTO;
import io.bootify.health_hive.repos.FileHashRepository;
import io.bootify.health_hive.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FileHashService {

    private final FileHashRepository fileHashRepository;

    public FileHashService(final FileHashRepository fileHashRepository) {
        this.fileHashRepository = fileHashRepository;
    }

    public List<FileHashDTO> findAll() {
        final List<FileHash> fileHashes = fileHashRepository.findAll(Sort.by("id"));
        return fileHashes.stream()
                .map(fileHash -> mapToDTO(fileHash, new FileHashDTO()))
                .toList();
    }

    public FileHashDTO get(final Long id) {
        return fileHashRepository.findById(id)
                .map(fileHash -> mapToDTO(fileHash, new FileHashDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FileHashDTO fileHashDTO) {
        final FileHash fileHash = new FileHash();
        mapToEntity(fileHashDTO, fileHash);
        return fileHashRepository.save(fileHash).getId();
    }

    public void update(final Long id, final FileHashDTO fileHashDTO) {
        final FileHash fileHash = fileHashRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(fileHashDTO, fileHash);
        fileHashRepository.save(fileHash);
    }

    public void delete(final Long id) {
        fileHashRepository.deleteById(id);
    }

    private FileHashDTO mapToDTO(final FileHash fileHash, final FileHashDTO fileHashDTO) {
        fileHashDTO.setId(fileHash.getId());
        fileHashDTO.setFileHash(fileHash.getFileHash());
        return fileHashDTO;
    }

    private FileHash mapToEntity(final FileHashDTO fileHashDTO, final FileHash fileHash) {
        fileHash.setFileHash(fileHashDTO.getFileHash());
        return fileHash;
    }

}
