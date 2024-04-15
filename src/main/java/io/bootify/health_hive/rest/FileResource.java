package io.bootify.health_hive.rest;
import io.bootify.health_hive.model.FileDTO;
import io.bootify.health_hive.model.LabDataUploadDTO;
import io.bootify.health_hive.service.FileService;
import io.bootify.health_hive.service.LabDataUploadService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
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
    private final LabDataUploadService labDataUploadService;


    public FileResource(final FileService fileService, LabDataUploadService labDataUploadService) {
        this.fileService = fileService;
        this.labDataUploadService = labDataUploadService;
    }

    @GetMapping
    public ResponseEntity<List<FileDTO>> getAllFiles() {
        return ResponseEntity.ok(fileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable(name = "id") final Long id) {
        FileDTO fileDTO = fileService.get(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDTO.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDTO.getName() + "\"")
                .body(fileService.getFile(fileDTO.getFilePath()));

    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFile(
            @RequestParam(name = "labRequestId") final Long labRequestId,
            @RequestPart("file") MultipartFile file) throws IOException {
        FileDTO fileDTO = new FileDTO();
        LabDataUploadDTO labDataUploadDTO = new LabDataUploadDTO();
        String FileType = file.getContentType();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        final Long createdFileId = labDataUploadService.create(labDataUploadDTO);
        String FilePath = fileService.saveFile(file);
        labDataUploadDTO.setDescription(fileName);
        labDataUploadDTO.setLabRequest(labRequestId);
        fileDTO.setName(fileName);
        fileDTO.setLabDataUpload(createdFileId);
        fileDTO.setType(FileType);
        fileDTO.setFilePath(FilePath);
        fileService.create(fileDTO);
        return new ResponseEntity<>(createdFileId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFile(
            @PathVariable(name = "id") final Long fileId,
            @RequestParam(name = "labRequestId") final Long labRequestId,
            @RequestPart("file") MultipartFile file) throws IOException {

        // Assuming you have a method to check if the file exists
        if (!fileService.exists(fileId)) {
            return ResponseEntity.notFound().build();
        }

        // Update the file
        String fileType = file.getContentType();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Update lab data upload description
        LabDataUploadDTO labDataUploadDTO = new LabDataUploadDTO();
        labDataUploadDTO.setDescription(fileName);
        labDataUploadDTO.setLabRequest(labRequestId);
        labDataUploadService.update(fileService.get(fileId).getLabDataUpload(), labDataUploadDTO);

        // Save the updated file
        String filePath = fileService.saveFile(file);

        // Update the file entity
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(fileId);
        fileDTO.setName(fileName);
        fileDTO.setLabDataUpload(fileService.get(fileId).getLabDataUpload());
        fileDTO.setType(fileType);
        fileDTO.setFilePath(filePath);
        fileService.update(fileId, fileDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFile(@PathVariable(name = "id") final Long id) {
        fileService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
