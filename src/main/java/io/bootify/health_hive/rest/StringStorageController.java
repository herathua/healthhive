package io.bootify.health_hive.rest;

import io.bootify.health_hive.service.StringStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api")
public class StringStorageController {

    @Autowired
    private StringStorageService stringStorageService;

    @PostMapping("/storeString")
    public String storeString(@RequestBody String inputString) {
        try {
            return stringStorageService.storeString(inputString);
        } catch (Exception e) {
            return "Error storing string: " + e.getMessage();
        }
    }

    @GetMapping("/retrieveString")
    public String retrieveString(@RequestParam BigInteger transactionId) {
        try {
            return stringStorageService.retrieveString(transactionId);
        } catch (Exception e) {
            return "Error retrieving string: " + e.getMessage();
        }
    }
}
