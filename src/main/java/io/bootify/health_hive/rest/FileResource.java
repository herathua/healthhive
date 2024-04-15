package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.FileDTO;
import io.bootify.health_hive.model.FileType;
import io.bootify.health_hive.model.LabDataUploadDTO;
import io.bootify.health_hive.service.FileService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFile(
            @RequestParam(name = "labRequestId") final Long labRequestId,
            @RequestPart("file") MultipartFile file
            /*,@RequestBody @Valid final FileDTO fileDTO*/) {

        String FileType = file.getContentType();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDTO fileDTO = new FileDTO();
        //final Long createdId = labDataUploadService.create(labDataUploadDTO);

        //labDataUploadDTO.setId(createdId);
        fileDTO.setName(fileName);
        fileDTO.setLabDataUpload(labRequestId);
        fileDTO.setType(io.bootify.health_hive.model.FileType.valueOf(FileType));

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
