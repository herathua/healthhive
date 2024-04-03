package io.bootify.health_hive.controller;


import io.bootify.health_hive.service.IPFSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class IPFSController {


    @Autowired
    private IPFSService ipfsService;

    @PostMapping(value = "upload")
    public String uploadFile(@RequestParam("file") MultipartFile file)
    {
        System.out.println("Received request for file upload");
        return ipfsService.saveFile(file);
    }

    @GetMapping(value = "file/{hash}")
    public ResponseEntity<byte[]> getFile(@PathVariable("hash") String hash) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", MediaType.ALL_VALUE);
        byte[] bytes = ipfsService.loadFile(hash);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bytes);

    }

}
