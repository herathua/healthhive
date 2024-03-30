package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.DataUploadReqeustDTO;
import io.bootify.health_hive.service.DataUploadReqeustService;
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
@RequestMapping(value = "/api/dataUploadReqeusts", produces = MediaType.APPLICATION_JSON_VALUE)
public class DataUploadReqeustResource {

    private final DataUploadReqeustService dataUploadReqeustService;

    public DataUploadReqeustResource(final DataUploadReqeustService dataUploadReqeustService) {
        this.dataUploadReqeustService = dataUploadReqeustService;
    }

    @GetMapping
    public ResponseEntity<List<DataUploadReqeustDTO>> getAllDataUploadReqeusts() {
        return ResponseEntity.ok(dataUploadReqeustService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataUploadReqeustDTO> getDataUploadReqeust(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(dataUploadReqeustService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDataUploadReqeust(
            @RequestBody @Valid final DataUploadReqeustDTO dataUploadReqeustDTO) {
        final Long createdId = dataUploadReqeustService.create(dataUploadReqeustDTO,null);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDataUploadReqeust(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DataUploadReqeustDTO dataUploadReqeustDTO) {
        dataUploadReqeustService.update(id, dataUploadReqeustDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDataUploadReqeust(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = dataUploadReqeustService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        dataUploadReqeustService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
