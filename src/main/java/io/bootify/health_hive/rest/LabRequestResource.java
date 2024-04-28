package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.LabRequestDTO;
import io.bootify.health_hive.service.LabRequestService;
import io.bootify.health_hive.util.ReferencedException;
import io.bootify.health_hive.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/labRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class LabRequestResource {
    private final LabRequestService labRequestService;

//    public LabRequestResource(final LabRequestService labRequestService) {
//        this.labRequestService = labRequestService;
//    }

//    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public LabRequestResource(LabRequestService labRequestService /*,SimpMessagingTemplate messagingTemplate*/) {
        this.labRequestService = labRequestService;
//        this.messagingTemplate = messagingTemplate;
    }


    @GetMapping("/{id}")
    public ResponseEntity<LabRequestDTO> getLabRequest(@PathVariable(name = "id") final Long id) {
        LabRequestDTO labRequestDTO = labRequestService.get(id);
        return ResponseEntity.ok(labRequestDTO);
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createLabRequest(
            @RequestBody @Valid final LabRequestDTO labRequestDTO) {
        final Long createdId = labRequestService.create(labRequestDTO);
        /*messagingTemplate.convertAndSend("/topic/lab/" + labRequestDTO, "Lab data updated"); // Sending WebSocket message*/
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLabRequest(@PathVariable(name = "id") final Long id,
                                                 @RequestBody @Valid final LabRequestDTO labRequestDTO) {
        labRequestService.update(id, labRequestDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLabRequest(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = labRequestService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        labRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
