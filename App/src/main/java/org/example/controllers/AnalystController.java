package org.example.controllers;

import com.nikak.pspkurssecurity.dto.ResponseMessage;
import com.nikak.pspkurssecurity.dto.AnalystProfileRequest;
import com.nikak.pspkurssecurity.entities.Analyst;
import com.nikak.pspkurssecurity.services.JWTService;
import com.nikak.pspkurssecurity.services.AnalystService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/analyst")
@RequiredArgsConstructor
public class AnalystController {

    private final JWTService jwtService;
    private final AnalystService analystService;


    @PutMapping("/pic/{analystId}")
    public ResponseEntity<ResponseMessage> updateAnalystPic(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam Long analystId
    ) {
        String message = "";
        try {
            message = analystService.updateAnalystImage(analystId, file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping("/profile/{analystId}")
    public ResponseEntity<Analyst> updateTeacherPic(
            @RequestBody AnalystProfileRequest analystProfileRequest,
            @RequestParam Long analystId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                analystService.updateAnalystProfile(analystProfileRequest, analystId)
        );
    }

    @DeleteMapping("/pic/{analystId}")
    public ResponseEntity<ResponseMessage> updateTeacherPic(
            @RequestParam Long analystId
    ) {
        String message = "";
        try {
            message = analystService.deleteAnalystImage(analystId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete pic !";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }


    @GetMapping("")
    public ResponseEntity<String> greeting(
            @RequestHeader("Authorization") String bearerToken) {
        // code that uses the language variable
        String username = jwtService.extractUserName(bearerToken.substring(7));
        return new ResponseEntity<String>(username, HttpStatus.OK);
    }


}

