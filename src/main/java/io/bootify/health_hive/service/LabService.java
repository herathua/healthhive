package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.Lab;
import io.bootify.health_hive.domain.LabRequest;
import io.bootify.health_hive.model.LabDTO;
import io.bootify.health_hive.model.LabRequestDTO;
import io.bootify.health_hive.repos.LabRepository;
import io.bootify.health_hive.repos.LabRequestRepository;
import io.bootify.health_hive.util.NotFoundException;
import io.bootify.health_hive.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class LabService {

    private final LabRepository labRepository;
    private final LabRequestRepository labRequestRepository;
    private final KeycloackService keycloackService;

    public LabService(final LabRepository labRepository,
                      final LabRequestRepository labRequestRepository,final KeycloackService keycloackService) {
        this.labRepository = labRepository;
        this.labRequestRepository = labRequestRepository;
        this.keycloackService = keycloackService;
    }

    public List<LabDTO> findAll() {
        final List<Lab> labs = labRepository.findAll(Sort.by("id"));
        return labs.stream()
                .map(lab -> mapToDTO(lab, new LabDTO()))
                .toList();
    }

    public LabDTO get(final Long id) {
        return labRepository.findById(id)
                .map(lab -> mapToDTO(lab, new LabDTO()))
                .orElseThrow(NotFoundException::new);
    }



    public Long create(final LabDTO labDTO) {
        final Lab lab = new Lab();
        mapToEntity(labDTO, lab);
        keycloackService.createLabInKeycloak(labDTO);

        return labRepository.save(lab).getId();
    }

    public String update(final Long id, final LabDTO labDTO) {
        final Lab lab = labRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(labDTO, lab);
        KeycloackService.updateLabInKeycloak(labDTO);
        labRepository.save(lab);
        return ("Lab updated successfully");
    }

    public void delete(final Long id) {
        labRepository.deleteById(id);
        keycloackService.deleteLabInKeycloak(get(id));

    }
    public void resetLabPassword(final Long id, final String tempPassword) {
        final Lab lab = labRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        keycloackService.resetLabPassword(id,tempPassword);
    }

    private LabDTO mapToDTO(final Lab lab, final LabDTO labDTO) {
        labDTO.setId(lab.getId());
        labDTO.setLabRegID(lab.getLabRegID());
        labDTO.setLabName(lab.getLabName());
        labDTO.setAddress(lab.getAddress());
        labDTO.setEmail(lab.getEmail());
        labDTO.setTelephone(lab.getTelephone());
        labDTO.setLabProfilePictureUrl(lab.getLabProfilePictureUrl());
        return labDTO;
    }

    private Lab mapToEntity(final LabDTO labDTO, final Lab lab) {
        lab.setLabRegID(labDTO.getLabRegID());
        lab.setLabName(labDTO.getLabName());
        lab.setAddress(labDTO.getAddress());
        lab.setEmail(labDTO.getEmail());
        lab.setTelephone(labDTO.getTelephone());
        lab.setLabProfilePictureUrl(labDTO.getLabProfilePictureUrl());
        return lab;
    }

    public boolean labRegIDExists(final String labRegID) {
        return labRepository.existsByLabRegIDIgnoreCase(labRegID);
    }

    public boolean emailExists(final String email) {
        return labRepository.existsByEmailIgnoreCase(email);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Lab lab = labRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final LabRequest labLabRequest = labRequestRepository.findFirstByLab(lab);
        if (labLabRequest != null) {
            referencedWarning.setKey("lab.labRequest.lab.referenced");
            referencedWarning.addParam(labLabRequest.getId());
            return referencedWarning;
        }
        return null;
    }

}
