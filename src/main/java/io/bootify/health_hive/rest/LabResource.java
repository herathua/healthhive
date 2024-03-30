package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.LabDTO;
import io.bootify.health_hive.service.LabService;
import io.bootify.health_hive.util.ReferencedException;
import io.bootify.health_hive.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/labs", produces = MediaType.APPLICATION_JSON_VALUE)
public class LabResource {

    private final LabService labService;

    public LabResource(final LabService labService) {
        this.labService = labService;
    }

    @GetMapping
    public ResponseEntity<List<LabDTO>> getAllLabs() {
        return ResponseEntity.ok(labService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabDTO> getLab(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(labService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLab(@RequestBody @Valid final LabDTO labDTO) {
        final Long createdId = labService.create(labDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLab(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LabDTO labDTO) {
        labService.update(id, labDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLab(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = labService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        labService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
