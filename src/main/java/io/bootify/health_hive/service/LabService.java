package io.bootify.health_hive.service;

import io.bootify.health_hive.domain.Lab;
import io.bootify.health_hive.domain.LabRequest;
import io.bootify.health_hive.model.LabDTO;
import io.bootify.health_hive.repos.LabRepository;
import io.bootify.health_hive.repos.LabRequestRepository;
import io.bootify.health_hive.util.NotFoundException;
import io.bootify.health_hive.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LabService {

    private final LabRepository labRepository;
    private final LabRequestRepository labRequestRepository;

    public LabService(final LabRepository labRepository,
                      final LabRequestRepository labRequestRepository) {
        this.labRepository = labRepository;
        this.labRequestRepository = labRequestRepository;
    }

    public List<LabDTO> findAll() {
        final List<Lab> labs = labRepository.findAll(Sort.by("email"));
        return labs.stream()
                .map(lab -> mapToDTO(lab, new LabDTO()))
                .toList();
    }

    public LabDTO get(final String email) {
        return labRepository.findById(email)
                .map(lab -> mapToDTO(lab, new LabDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final LabDTO labDTO) {
        final Lab lab = new Lab();
        mapToEntity(labDTO, lab);
        lab.setEmail(labDTO.getEmail());
        return labRepository.save(lab).getEmail();
    }

    public void update(final String email, final LabDTO labDTO) {
        final Lab lab = labRepository.findById(email)
                .orElseThrow(NotFoundException::new);
        mapToEntity(labDTO, lab);
        labRepository.save(lab);
    }

    public void delete(final String email) {
        labRepository.deleteById(email);
    }

    private LabDTO mapToDTO(final Lab lab, final LabDTO labDTO) {
        labDTO.setEmail(lab.getEmail());
        labDTO.setLabRegID(lab.getLabRegID());
        labDTO.setLabName(lab.getLabName());
        labDTO.setAddress(lab.getAddress());
        labDTO.setTelephone(lab.getTelephone());
        return labDTO;
    }

    private Lab mapToEntity(final LabDTO labDTO, final Lab lab) {
        lab.setLabRegID(labDTO.getLabRegID());
        lab.setLabName(labDTO.getLabName());
        lab.setAddress(labDTO.getAddress());
        lab.setTelephone(labDTO.getTelephone());
        return lab;
    }

    public boolean emailExists(final String email) {
        return labRepository.existsByEmailIgnoreCase(email);
    }

    public boolean labRegIDExists(final String labRegID) {
        return labRepository.existsByLabRegIDIgnoreCase(labRegID);
    }

    public ReferencedWarning getReferencedWarning(final String email) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Lab lab = labRepository.findById(email)
                .orElseThrow(NotFoundException::new);
        final LabRequest labEmailLabRequest = labRequestRepository.findFirstByLabEmail(lab);
        if (labEmailLabRequest != null) {
            referencedWarning.setKey("lab.labRequest.labEmail.referenced");
            referencedWarning.addParam(labEmailLabRequest.getId());
            return referencedWarning;
        }
        return null;
    }

}
