//package io.bootify.health_hive.rest;
//
//
//import io.bootify.health_hive.service.IPFSService;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@Log4j2
//@RestController
//@RequestMapping(value = "/api")
//public class IPFSController {
//    @Autowired
//    private IPFSService ipfsService;
//
//    @PostMapping(value = "/ipfsOld/upload")
//    public String uploadFile(@RequestParam("file") MultipartFile file) {
//        return ipfsService.saveFile(file);
//    }
//
//    @GetMapping(value = "/ipfsOld/{hash}")
//    public ResponseEntity<byte[]> getFile(@PathVariable("hash") String hash) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-type", MediaType.ALL_VALUE);
//        byte[] bytes = ipfsService.loadFile(hash);
//        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(bytes);
//    }
//}
