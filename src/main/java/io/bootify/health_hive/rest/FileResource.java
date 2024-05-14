package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.FileDTO;
import io.bootify.health_hive.model.LabDataUploadDTO;
import io.bootify.health_hive.service.FileService;
import io.bootify.health_hive.service.LabDataUploadService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
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
    // final LabDataUploadService labDataUploadService;
    public FileResource(final FileService fileService/*, LabDataUploadService labDataUploadService*/) {
        this.fileService = fileService;
        //this.labDataUploadService = labDataUploadService;
    }
    /**
     * Retrieve all files.
     */
    @GetMapping
    public ResponseEntity<List<FileDTO>> getAllFiles() {
        return ResponseEntity.ok(fileService.findAll());
    }
    /**
     * Retrieve a file by ID and return it as a byte array.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable(name = "id") final Long id) {
        FileDTO fileDTO = fileService.get(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDTO.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDTO.getName() + "\"")
                .body(fileService.getFile(fileDTO.getFilePath()));
    }
    /**
     * Create a new file.
     */
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFile(
            @RequestParam(name = "labRequestId") final Long labRequestId,
            //@RequestBody final Long labRequestId,
            //@RequestBody final LocalDate CreatedDate,
            @RequestPart("file") MultipartFile file) throws IOException {
        FileDTO fileDTO = new FileDTO();
        String fileType = file.getContentType();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Create lab data upload entity
        String filePath = fileService.saveFile(file);
        fileDTO.setCreatedDate(java.time.LocalDate.now());
        //fileDTO.setCreatedDate(CreatedDate);
        fileDTO.setLabDataUpload(labRequestId);
        // Populate DTOs with data
        fileDTO.setName(fileName);
        fileDTO.setType(fileType);
        fileDTO.setFilePath(filePath);
        final Long createdId = fileService.create(fileDTO);

        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }
    /**
     * Update an existing file.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFile(
            @PathVariable(name = "id") final Long fileId,
            @RequestParam(name = "labRequestId") final Long labRequestId,
            @RequestPart("file") MultipartFile file) throws IOException {
        if (!fileService.exists(fileId)) {
            return ResponseEntity.notFound().build();
        }
        // Extract file details
        String fileType = file.getContentType();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = fileService.saveFile(file);
        // Update file entity
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(fileId);
        fileDTO.setName(fileName);
        fileDTO.setCreatedDate(java.time.LocalDate.now());
        fileDTO.setLabDataUpload(labRequestId);
        fileDTO.setLabDataUpload(fileService.get(fileId).getLabDataUpload());
        fileDTO.setType(fileType);
        fileDTO.setFilePath(filePath);
        fileService.update(fileId, fileDTO);

        return new ResponseEntity<>(fileId, HttpStatus.CREATED);
    }
    /**
     * Delete a file by ID.
     */
    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<FileDTO> deleteFile(@PathVariable(name = "id") final Long id) {
        FileDTO fileDTO = fileService.get(id); // Retrieve file information
        if (fileDTO != null) {
            // Get the file path from the file DTO
            String filePath = fileDTO.getFilePath();
            // Delete the file from the file system
            try {
                Path path = Paths.get(filePath);
                Files.deleteIfExists(path);
            } catch (IOException e) {
                // Handle any errors that occur during file deletion
                // For example, log the error
                e.printStackTrace();
                // Return an appropriate response to the client
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            // Once the file is deleted from the file system, delete it from the database
            fileService.delete(id);
            return ResponseEntity.ok(fileDTO);
        } else {
            // If the file does not exist in the database, return a not found response
            return ResponseEntity.notFound().build();
        }
    }

}
