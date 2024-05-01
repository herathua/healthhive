
package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.DataUploadRequestDTO;
import io.bootify.health_hive.service.DataUploadRequestService;
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
@RequestMapping(value = "/api/dataUploadRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataUploadRequestResource {

    private final DataUploadRequestService dataUploadRequestService;

    public DataUploadRequestResource(final DataUploadRequestService dataUploadRequestService) {
        this.dataUploadRequestService = dataUploadRequestService;
    }

    @GetMapping
    public ResponseEntity<List<DataUploadRequestDTO>> getAllDataUploadRequests() {
        return ResponseEntity.ok(dataUploadRequestService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataUploadRequestDTO> getDataUploadRequest(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(dataUploadRequestService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDataUploadRequest(
            @RequestBody @Valid final DataUploadRequestDTO dataUploadRequestDTO) {
       final Long createdId = dataUploadRequestService.create(dataUploadRequestDTO);
      return new ResponseEntity<>(createdId, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDataUploadRequest(@PathVariable(name = "id") final Long id,
                                                        @RequestBody @Valid final DataUploadRequestDTO dataUploadRequestDTO) {
        dataUploadRequestService.update(id, dataUploadRequestDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDataUploadRequest(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = dataUploadRequestService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        dataUploadRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
