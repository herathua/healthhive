package io.bootify.health_hive.rest;

import io.bootify.health_hive.model.FaqDTO;
import io.bootify.health_hive.service.FaqService;
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
@RequestMapping(value = "/api/faqs", produces = MediaType.APPLICATION_JSON_VALUE)
public class FaqResource {

    private final FaqService faqService;

    public FaqResource(final FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public ResponseEntity<List<FaqDTO>> getAllFaqs() {
        return ResponseEntity.ok(faqService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaqDTO> getFaq(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(faqService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFaq(@RequestBody @Valid final FaqDTO faqDTO) {
        final Long createdId = faqService.create(faqDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFaq(@PathVariable(name = "id") final Long id,
                                          @RequestBody @Valid final FaqDTO faqDTO) {
        faqService.update(id, faqDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFaq(@PathVariable(name = "id") final Long id) {
        faqService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

