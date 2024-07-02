package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.FileHashDTO;
import io.bootify.health_hive.service.FileHashService;
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
@RequestMapping(value = "/api/fileHashes", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileHashResource {

    private final FileHashService fileHashService;

    public FileHashResource(final FileHashService fileHashService) {
        this.fileHashService = fileHashService;
    }

    @GetMapping
    public ResponseEntity<List<FileHashDTO>> getAllFileHashes() {
        return ResponseEntity.ok(fileHashService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileHashDTO> getFileHash(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(fileHashService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFileHash(@RequestBody @Valid final FileHashDTO fileHashDTO) {
        final Long createdId = fileHashService.create(fileHashDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFileHash(@PathVariable(name = "id") final Long id,
                                               @RequestBody @Valid final FileHashDTO fileHashDTO) {
        fileHashService.update(id, fileHashDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFileHash(@PathVariable(name = "id") final Long id) {
        fileHashService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
