package io.bootify.health_hive.rest;

import io.bootify.health_hive.service.PinataService;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/pinata")
public class PinataController {

    @Autowired
    private PinataService pinataService;

    @PostMapping("/pin")
    public String pinFile(@RequestParam String filePath) {
        try {
            return pinataService.pinFileToIPFS(filePath);
        } catch (IOException | ParseException e) {
            return "Error pinning file: " + e.getMessage();
        }
    }
}