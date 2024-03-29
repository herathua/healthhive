package io.bootify.health_hivw.rest;

import io.bootify.health_hivw.model.LabRequestDTO;
import io.bootify.health_hivw.service.LabRequestService;
import io.bootify.health_hivw.util.ReferencedException;
import io.bootify.health_hivw.util.ReferencedWarning;
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
@RequestMapping(value = "/api/labRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class LabRequestResource {

    private final LabRequestService labRequestService;

    public LabRequestResource(final LabRequestService labRequestService) {
        this.labRequestService = labRequestService;
    }

    @GetMapping
    public ResponseEntity<List<LabRequestDTO>> getAllLabRequests() {
        return ResponseEntity.ok(labRequestService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabRequestDTO> getLabRequest(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(labRequestService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLabRequest(
            @RequestBody @Valid final LabRequestDTO labRequestDTO) {
        final Long createdId = labRequestService.create(labRequestDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLabRequest(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LabRequestDTO labRequestDTO) {
        labRequestService.update(id, labRequestDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLabRequest(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = labRequestService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        labRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
