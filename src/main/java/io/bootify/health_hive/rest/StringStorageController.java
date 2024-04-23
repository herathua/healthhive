package io.bootify.health_hive.rest;

import io.bootify.health_hive.service.StringStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import java.math.BigInteger;

@RestController
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
