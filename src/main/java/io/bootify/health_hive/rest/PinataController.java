package io.bootify.health_hive.rest;

import io.bootify.health_hive.service.PinataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api")
public class PinataController {

    @Autowired
    private PinataService pinataService;

    @PostMapping(value = "/ipfs/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return pinataService.saveFile(file);
    }

    @GetMapping(value = "/ipfs/{hash}")
    public ResponseEntity<byte[]> getFile(@PathVariable("hash") String hash) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", MediaType.ALL_VALUE);
        byte[] bytes = pinataService.loadFile(hash);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bytes);
    }
}
