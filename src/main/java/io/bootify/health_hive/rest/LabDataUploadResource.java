package io.bootify.health_hive.rest;

import io.bootify.health_hive.domain.LabDataUpload;
import io.bootify.health_hive.model.LabDataUploadDTO;
import io.bootify.health_hive.service.LabDataUploadService;
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
@RequestMapping(value = "/api/labDataUploads", produces = MediaType.APPLICATION_JSON_VALUE)
public class LabDataUploadResource {

    private final LabDataUploadService labDataUploadService;

    public LabDataUploadResource(final LabDataUploadService labDataUploadService) {
        this.labDataUploadService = labDataUploadService;
    }

    @GetMapping
    public ResponseEntity<List<LabDataUploadDTO>> getAllLabDataUploads() {
        return ResponseEntity.ok(labDataUploadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabDataUploadDTO> getLabDataUpload(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(labDataUploadService.get(id));
    }
    @GetMapping("/lab-data-uploads/{labRequestId}")
    public ResponseEntity<String> getLabDataUploads(@PathVariable Long labRequestId) {
        try {
            LabDataUploadDTO labDataUploadDTO = labDataUploadService.searchByLabRequest(labRequestId);
            if (labDataUploadDTO != null) {
                return new ResponseEntity<>("Uploaded", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Not Uploaded", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLabDataUpload(
            @RequestBody @Valid final LabDataUploadDTO labDataUploadDTO) {
        final Long createdId = labDataUploadService.create(labDataUploadDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLabDataUpload(@PathVariable(name = "id") final Long id,
                                                    @RequestBody @Valid final LabDataUploadDTO labDataUploadDTO) {
        labDataUploadService.update(id, labDataUploadDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLabDataUpload(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = labDataUploadService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        labDataUploadService.delete(id);
        return ResponseEntity.noContent().build();
    }

}