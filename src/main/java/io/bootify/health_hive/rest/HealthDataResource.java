package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.HealthDataDTO;
import io.bootify.health_hive.service.HealthDataService;
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
@RequestMapping(value = "/api/healthDatas", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthDataResource {

    private final HealthDataService healthDataService;

    public HealthDataResource(final HealthDataService healthDataService) {
        this.healthDataService = healthDataService;
    }

    @GetMapping
    public ResponseEntity<List<HealthDataDTO>> getAllHealthDatas() {
        return ResponseEntity.ok(healthDataService.findAll());
    }

    @GetMapping("/{healthDataId}")
    public ResponseEntity<HealthDataDTO> getHealthData(
            @PathVariable(name = "healthDataId") final Long healthDataId) {
        return ResponseEntity.ok(healthDataService.get(healthDataId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createHealthData(
            @RequestBody @Valid final HealthDataDTO healthDataDTO) {
        final Long createdHealthDataId = healthDataService.create(healthDataDTO);
        return new ResponseEntity<>(createdHealthDataId, HttpStatus.CREATED);
    }

    @PutMapping("/{healthDataId}")
    public ResponseEntity<Long> updateHealthData(
            @PathVariable(name = "healthDataId") final Long healthDataId,
            @RequestBody @Valid final HealthDataDTO healthDataDTO) {
        healthDataService.update(healthDataId, healthDataDTO);
        return ResponseEntity.ok(healthDataId);
    }

    @DeleteMapping("/{healthDataId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteHealthData(
            @PathVariable(name = "healthDataId") final Long healthDataId) {
        healthDataService.delete(healthDataId);
        return ResponseEntity.noContent().build();
    }

}
