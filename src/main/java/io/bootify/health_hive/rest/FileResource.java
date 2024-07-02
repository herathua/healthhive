package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.FileDTO;
import io.bootify.health_hive.model.UserFileDTO;
import io.bootify.health_hive.service.FileService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.bootify.health_hive.service.StringStorageService;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(value = "/api/files", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileResource {

    private final FileService fileService;
    private final StringStorageService stringStorageService;


    public FileResource(final FileService fileService, final StringStorageService stringStorageService) {
        this.fileService = fileService;
        this.stringStorageService = stringStorageService;
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
    public ResponseEntity<List<UserFileDTO>> getFilesByUserId(@PathVariable(name = "userId") final Long userId) {
        return ResponseEntity.ok(fileService.findAllByUserId(userId));
    }

    @GetMapping("/latestFive/{dataUploadRequestId}/{labDataUploadId}")
    public ResponseEntity<List<FileDTO>> getLatestFiveFiles(@PathVariable(name = "dataUploadRequestId") final Long dataUploadRequestId,
                                                            @PathVariable(name = "labDataUploadId") final Long labDataUploadId) {
        return ResponseEntity.ok(fileService.findLatestFile(dataUploadRequestId, labDataUploadId));
    }



    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFile(@RequestBody @Valid final FileDTO fileDTO) throws Exception {


        final Long createdId = fileService.create(fileDTO);
//        final String txId = stringStorageService.storeString(fileDTO.getFileHash());
        try{
//            return null;
            return new ResponseEntity<>(createdId, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(createdId, HttpStatus.INTERNAL_SERVER_ERROR);
//            return null;
        }
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
