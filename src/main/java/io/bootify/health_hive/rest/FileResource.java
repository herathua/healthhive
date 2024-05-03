package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.FileDTO;
import io.bootify.health_hive.service.FileService;
import io.bootify.health_hive.service.DataUploadRequestService;
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
@RequestMapping(value = "/api/files", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileResource {

    private final FileService fileService;


    public FileResource(final FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<List<FileDTO>> getAllFiles() {
        return ResponseEntity.ok(fileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFile(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(fileService.get(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FileDTO>> getFilesByUserId(@PathVariable(name = "userId") final Long userId) {

        return ResponseEntity.ok(fileService.findAllByUserId(userId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFile(@RequestBody @Valid final FileDTO fileDTO) {

        final Long createdId = fileService.create(fileDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFile(@PathVariable(name = "id") final Long id,
                                           @RequestBody @Valid final FileDTO fileDTO) {
        fileService.update(id, fileDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFile(@PathVariable(name = "id") final Long id) {
        fileService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
