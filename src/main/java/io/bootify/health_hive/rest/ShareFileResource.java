package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.ShareFileDTO;
import io.bootify.health_hive.service.ShareFileService;
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
@RequestMapping(value = "/api/shareFiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShareFileResource {

    private final ShareFileService shareFileService;

    public ShareFileResource(final ShareFileService shareFileService) {
        this.shareFileService = shareFileService;
    }

    @GetMapping
    public ResponseEntity<List<ShareFileDTO>> getAllShareFiles() {
        return ResponseEntity.ok(shareFileService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShareFileDTO> getShareFile(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(shareFileService.get(id));
    }

    @GetMapping("/user/{doctorId}")
    public ResponseEntity<List<ShareFileDTO>> getSharedFiles(@PathVariable(name = "doctorId") final Long doctorId) {
        return ResponseEntity.ok(shareFileService.findByDoctorId(doctorId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createShareFile(
            @RequestBody @Valid final ShareFileDTO shareFileDTO) {
        final Long createdId = shareFileService.create(shareFileDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateShareFile(@PathVariable(name = "id") final Long id,
                                                @RequestBody @Valid final ShareFileDTO shareFileDTO) {
        shareFileService.update(id, shareFileDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteShareFile(@PathVariable(name = "id") final Long id) {
        shareFileService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
