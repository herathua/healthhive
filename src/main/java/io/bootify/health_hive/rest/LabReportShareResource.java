package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.LabReportShareDTO;
import io.bootify.health_hive.service.LabReportShareService;
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
@RequestMapping(value = "/api/labReportShares", produces = MediaType.APPLICATION_JSON_VALUE)
public class LabReportShareResource {

    private final LabReportShareService labReportShareService;

    public LabReportShareResource(final LabReportShareService labReportShareService) {
        this.labReportShareService = labReportShareService;
    }

    @GetMapping
    public ResponseEntity<List<LabReportShareDTO>> getAllLabReportShares() {
        return ResponseEntity.ok(labReportShareService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabReportShareDTO> getLabReportShare(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(labReportShareService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLabReportShare(
            @RequestBody @Valid final LabReportShareDTO labReportShareDTO) {
        final Long createdId = labReportShareService.create(labReportShareDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLabReportShare(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LabReportShareDTO labReportShareDTO) {
        labReportShareService.update(id, labReportShareDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLabReportShare(@PathVariable(name = "id") final Long id) {
        labReportShareService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
