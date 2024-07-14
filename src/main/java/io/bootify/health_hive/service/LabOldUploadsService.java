package io.bootify.health_hive.service;
import io.bootify.health_hive.domain.LabOldUploads;
import io.bootify.health_hive.model.LabOldUploadsDTO;
import io.bootify.health_hive.repos.LabOldUploadsRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import io.bootify.health_hive.util.NotFoundException;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class LabOldUploadsService {

    private final LabOldUploadsRepository labOldUploadsRepository;
    public LabOldUploadsService(final LabOldUploadsRepository labOldUploadsRepository) {
        this.labOldUploadsRepository = labOldUploadsRepository;
    }

    public List<LabOldUploadsDTO> findAll() {
        final List<LabOldUploads> labOldUploadses = labOldUploadsRepository.findAll(Sort.by("id"));
        return labOldUploadses.stream()
                .map(labOldUploads -> mapToDTO(labOldUploads, new LabOldUploadsDTO()))
                .toList();
    }

    public LabOldUploadsDTO get(final Long id) {
        return labOldUploadsRepository.findById(id)
                .map(labOldUploads -> mapToDTO(labOldUploads, new LabOldUploadsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LabOldUploadsDTO labOldUploadsDTO) {
        final LabOldUploads labOldUploads = new LabOldUploads();
        mapToEntity(labOldUploadsDTO, labOldUploads);
        return labOldUploadsRepository.save(labOldUploads).getId();
    }

    public void update(final Long id, final LabOldUploadsDTO labOldUploadsDTO) {
        final LabOldUploads labOldUploads = labOldUploadsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(labOldUploadsDTO, labOldUploads);
        labOldUploadsRepository.save(labOldUploads);
    }

    public void delete(final Long id) {
        labOldUploadsRepository.deleteById(id);
    }
    public List<LabOldUploads> findLabOldUploadsByLabidAndDateRange(Long labid, OffsetDateTime startDate, OffsetDateTime endDate) {
        return labOldUploadsRepository.findByLabidAndDateCreatedBetween(labid, startDate, endDate);
    }

    private LabOldUploadsDTO mapToDTO(final LabOldUploads labOldUploads,
                                      final LabOldUploadsDTO labOldUploadsDTO) {
        labOldUploadsDTO.setId(labOldUploads.getId());
        labOldUploadsDTO.setLabid(labOldUploads.getLabid());
        labOldUploadsDTO.setDescription(labOldUploads.getDescription());
        labOldUploadsDTO.setFileName(labOldUploads.getFileName());
        labOldUploadsDTO.setLabRequestId(labOldUploads.getLabRequestId());
        return labOldUploadsDTO;
    }

    private LabOldUploads mapToEntity(final LabOldUploadsDTO labOldUploadsDTO,
                                      final LabOldUploads labOldUploads) {
        labOldUploads.setLabid(labOldUploadsDTO.getLabid());
        labOldUploads.setDescription(labOldUploadsDTO.getDescription());
        labOldUploads.setFileName(labOldUploadsDTO.getFileName());
        labOldUploads.setLabRequestId(labOldUploadsDTO.getLabRequestId());
        return labOldUploads;
    }
}
