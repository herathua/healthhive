package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.Lab;
import io.bootify.health_hive.domain.LabRequest;
import io.bootify.health_hive.model.LabDTO;
import io.bootify.health_hive.repos.LabRepository;
import io.bootify.health_hive.repos.LabRequestRepository;
import io.bootify.health_hive.util.NotFoundException;
import io.bootify.health_hive.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabService {

    private final LabRepository labRepository;
    private final LabRequestRepository labRequestRepository;
    private final KeycloakService keycloakService;

    public LabService(final LabRepository labRepository,
                      final LabRequestRepository labRequestRepository, final KeycloakService keycloakService) {
        this.labRepository = labRepository;
        this.labRequestRepository = labRequestRepository;
        this.keycloakService = keycloakService;
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

    public LabDTO findByEmail(String email) {
        return labRepository.findAllByEmail(email)
                .map(lab -> mapToDTO(lab, new LabDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LabDTO labDTO) {
        final Lab lab = new Lab();
        mapToEntity(labDTO, lab);
        keycloakService.createLabInKeycloak(labDTO);
        return labRepository.save(lab).getId();
    }

    public String update(final Long id, final LabDTO labDTO) {
        final Lab lab = labRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(labDTO, lab);
        labRepository.save(lab);
        keycloakService.editLabDetails(labDTO); // Update lab details in Keycloak
        return "Lab updated successfully";
    }

    public void delete(final Long id) {
        Lab lab = labRepository.findById(id).orElseThrow(NotFoundException::new);
        labRepository.deleteById(id);
        keycloakService.deleteLabInKeycloak(lab.getEmail()); // Delete lab user in Keycloak
    }

    public void resetLabPassword(final Long id, final String tempPassword) {
        final Lab lab = labRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        keycloakService.resetLabPassword(id, tempPassword);
    }

    private LabDTO mapToDTO(final Lab lab, final LabDTO labDTO) {
        labDTO.setId(lab.getId());
        labDTO.setLabRegID(lab.getLabRegID());
        labDTO.setLabName(lab.getLabName());
        labDTO.setAddress(lab.getAddress());
        labDTO.setEmail(lab.getEmail());
        labDTO.setTelephone(lab.getTelephone());
        return labDTO;
    }

    private Lab mapToEntity(final LabDTO labDTO, final Lab lab) {
        lab.setLabRegID(labDTO.getLabRegID());
        lab.setLabName(labDTO.getLabName());
        lab.setAddress(labDTO.getAddress());
        lab.setEmail(labDTO.getEmail());
        lab.setTelephone(labDTO.getTelephone());
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
