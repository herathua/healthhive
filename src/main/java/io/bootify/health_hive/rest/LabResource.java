package io.bootify.health_hive.rest;

import io.bootify.health_hive.domain.Lab;
import io.bootify.health_hive.model.LabDTO;
import io.bootify.health_hive.model.LabLoginDTO;
import io.bootify.health_hive.repos.LabRepository;
import io.bootify.health_hive.service.KeycloakService;
import io.bootify.health_hive.service.LabService;
import io.bootify.health_hive.util.NotFoundException;
import io.bootify.health_hive.util.ReferencedException;
import io.bootify.health_hive.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/labs", produces = MediaType.APPLICATION_JSON_VALUE)
public class LabResource {

    private final LabService labService;
    private final KeycloakService  keycloakService;
    private final LabRepository labRepository;

    public LabResource(final LabService labService, final KeycloakService keycloakService,final LabRepository labRepository){
        this.labService = labService;
        this.keycloakService = keycloakService;
        this.labRepository = labRepository;
    }

    @GetMapping
    public ResponseEntity<List<LabDTO>> getAllLabs() {
        return ResponseEntity.ok(labService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabDTO> getLab(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(labService.get(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<LabDTO> findAllByLabId(@PathVariable String email) {
        Lab lab = labRepository.findAllByEmail(email);
        return ResponseEntity.ok(labService.get(lab.getId()));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLab(@RequestBody @Valid final LabDTO labDTO) {
        final Long createdId = labService.create(labDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?>  login (@RequestBody LabLoginDTO labLoginDTO) {

        return null;
    }


    @PutMapping("/labs/{id}/reset-password")
    public void resetLabPassword(@PathVariable Long id, @RequestParam String tempPassword) {
        labService.resetLabPassword(id, tempPassword);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLab(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LabDTO labDTO) {
        labService.update(id, labDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Boolean> deleteLab(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = labService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        return ResponseEntity.ok(labService.delete(id));
    }

}
