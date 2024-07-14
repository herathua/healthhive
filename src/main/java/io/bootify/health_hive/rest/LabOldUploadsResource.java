package io.bootify.health_hive.rest;
import io.bootify.health_hive.domain.LabOldUploads;
import io.bootify.health_hive.model.LabOldUploadsDTO;
import io.bootify.health_hive.service.LabOldUploadsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;


@RestController
@RequestMapping(value = "/api/labOldUploadss", produces = MediaType.APPLICATION_JSON_VALUE)
public class LabOldUploadsResource {

    private final LabOldUploadsService labOldUploadsService;

    public LabOldUploadsResource(final LabOldUploadsService labOldUploadsService) {
        this.labOldUploadsService = labOldUploadsService;
    }

    @GetMapping
    public ResponseEntity<List<LabOldUploadsDTO>> getAllLabOldUploadss() {
        return ResponseEntity.ok(labOldUploadsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabOldUploadsDTO> getLabOldUploads(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(labOldUploadsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLabOldUploads(
            @RequestBody @Valid final LabOldUploadsDTO labOldUploadsDTO) {
        final Long createdId = labOldUploadsService.create(labOldUploadsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLabOldUploads(@PathVariable(name = "id") final Long id,
                                                    @RequestBody @Valid final LabOldUploadsDTO labOldUploadsDTO) {
        labOldUploadsService.update(id, labOldUploadsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLabOldUploads(@PathVariable(name = "id") final Long id) {
        labOldUploadsService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/lab-old-uploads")
    public List<LabOldUploads> getLabOldUploadsByLabidAndDateRange(
            @RequestParam Long labid,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endDate
    ) {
        return labOldUploadsService.findLabOldUploadsByLabidAndDateRange(labid, startDate, endDate);
    }

}
