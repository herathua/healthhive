package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.LabReportShare;
import io.bootify.health_hive.domain.ShareFile;
import io.bootify.health_hive.model.ShareFileDTO;
import io.bootify.health_hive.repos.LabReportShareRepository;
import io.bootify.health_hive.repos.ShareFileRepository;
import io.bootify.health_hive.util.NotFoundException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ShareFileService {

    private final ShareFileRepository shareFileRepository;
    private final LabReportShareRepository labReportShareRepository;

    public ShareFileService(final ShareFileRepository shareFileRepository,
                            final LabReportShareRepository labReportShareRepository) {
        this.shareFileRepository = shareFileRepository;
        this.labReportShareRepository = labReportShareRepository;
    }

    public List<ShareFileDTO> findAll() {
        final List<ShareFile> shareFiles = shareFileRepository.findAll(Sort.by("id"));
        return shareFiles.stream()
                .map(shareFile -> mapToDTO(shareFile, new ShareFileDTO()))
                .toList();
    }

    public ShareFileDTO get(final Long id) {
        return shareFileRepository.findById(id)
                .map(shareFile -> mapToDTO(shareFile, new ShareFileDTO()))
                .orElseThrow(NotFoundException::new);
    }
    public List<ShareFileDTO> findByDoctorId(final Long doctorId) {
        final List<ShareFile> shareFiles = shareFileRepository.findByDoctorId(doctorId);
        return shareFiles.stream()
                .map(shareFile -> mapToDTO(shareFile, new ShareFileDTO()))
                .toList();
    }

    public Long create(final ShareFileDTO shareFileDTO) {
        final ShareFile shareFile = new ShareFile();
        mapToEntity(shareFileDTO, shareFile);
        return shareFileRepository.save(shareFile).getId();
    }

    public void update(final Long id, final ShareFileDTO shareFileDTO) {
        final ShareFile shareFile = shareFileRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(shareFileDTO, shareFile);
        shareFileRepository.save(shareFile);
    }

    public void delete(final Long id) {
        shareFileRepository.deleteById(id);
    }

    private ShareFileDTO mapToDTO(final ShareFile shareFile, final ShareFileDTO shareFileDTO) {
        shareFileDTO.setId(shareFile.getId());
        shareFileDTO.setFileHash(shareFile.getFileHash());
        shareFileDTO.setDoctorId(shareFile.getDoctorId());
        shareFileDTO.setLabReportShare(shareFile.getLabReportShare() == null ? null : shareFile.getLabReportShare().getId());
        return shareFileDTO;
    }

    private ShareFile mapToEntity(final ShareFileDTO shareFileDTO, final ShareFile shareFile) {
        shareFile.setFileHash(shareFileDTO.getFileHash());
        shareFile.setDoctorId(shareFileDTO.getDoctorId());
        final LabReportShare labReportShare = shareFileDTO.getLabReportShare() == null ? null : labReportShareRepository.findById(shareFileDTO.getLabReportShare())
                .orElseThrow(() -> new NotFoundException("labReportShare not found"));
        shareFile.setLabReportShare(labReportShare);
        return shareFile;
    }

}
