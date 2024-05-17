package org.example.controllers;

import com.nikak.pspkurssecurity.dto.RatingRequest;
import com.nikak.pspkurssecurity.dto.ResponseMessage;
import com.nikak.pspkurssecurity.dto.SessionRequest;
import com.nikak.pspkurssecurity.entities.Session;
import com.nikak.pspkurssecurity.services.ClientService;
import com.nikak.pspkurssecurity.services.JWTService;
import com.nikak.pspkurssecurity.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/client")
@RequiredArgsConstructor
public class ClientController {
    private final JWTService jwtService;
    private final ClientService clientService;
    private final SessionService sessionService;

    @PutMapping("/rating")//работает
    public ResponseEntity<String> addRating(
            @RequestBody RatingRequest ratingRequest,
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        if (ratingRequest.getRating() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "you cannot assign null value "
            );
        } else if (ratingRequest.getRating() < 1 || ratingRequest.getRating() > 10) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "you cannot assign a rating greater than 10 or less than 1 "
            );
        }

        if (ratingRequest.getAnalystId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "you cannot assign a rating without specifying the analyst"
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                clientService.addRating(ratingRequest, email)
        );
    }

    @DeleteMapping("/rating/{ratingId}")//работает
    public ResponseEntity<String> deleteRating(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable Long ratingId
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        try {
            String message = clientService.deleteRatingById(ratingId, email);
            return ResponseEntity.status(HttpStatus.OK).body(
                    message
            );

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getMessage()
            );
        }

    }

    @GetMapping("/rating")
    public ResponseEntity<List<Object[]>> getRatings(
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        return ResponseEntity.status(HttpStatus.OK).body(
                clientService.getRatings(email)
        );
    }

    @GetMapping
    public ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok("hello student");
    }

    @PostMapping(path = "add")
    public ResponseEntity<String> addSession(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody SessionRequest sessionRequest
    ) {
        try {
            String email = jwtService.extractUserName(bearerToken.substring(7));
            return ResponseEntity.status(HttpStatus.OK).body(
                    sessionService.addNewSession(sessionRequest,email)
            );

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    e.getMessage()
            );
        }
    }

    @DeleteMapping("/session/{sessionId}")
    public ResponseEntity<ResponseMessage> deleteSession(
            @PathVariable Long sessionId
    ){
        String message = "";
        try {
            message =  sessionService.deleteSession(sessionId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete session!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getClientSessions(
            @RequestHeader("Authorization") String bearerToken
    ) {
        String email = jwtService.extractUserName(bearerToken.substring(7));
        return ResponseEntity.status(HttpStatus.OK).body(
                clientService.getClientSessions(email)
        );
    }
}
