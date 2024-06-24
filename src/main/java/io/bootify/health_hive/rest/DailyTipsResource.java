package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.DailyTipsDTO;
import io.bootify.health_hive.service.DailyTipsService;
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
@RequestMapping(value = "/api/dailyTips", produces = MediaType.APPLICATION_JSON_VALUE)
public class DailyTipsResource {

    private final DailyTipsService dailyTipsService;

    public DailyTipsResource(final DailyTipsService dailyTipsService) {
        this.dailyTipsService = dailyTipsService;
    }

    @GetMapping
    public ResponseEntity<List<DailyTipsDTO>> getAllDailyTipss() {
        return ResponseEntity.ok(dailyTipsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DailyTipsDTO> getDailyTips(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(dailyTipsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDailyTips(
            @RequestBody @Valid final DailyTipsDTO dailyTipsDTO) {
        final Long createdId = dailyTipsService.create(dailyTipsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDailyTips(@PathVariable(name = "id") final Long id,
                                                @RequestBody @Valid final DailyTipsDTO dailyTipsDTO) {
        dailyTipsService.update(id, dailyTipsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDailyTips(@PathVariable(name = "id") final Long id) {
        dailyTipsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
