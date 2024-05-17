package org.example.controllers;


import com.nikak.pspkurssecurity.entities.Favor;
import com.nikak.pspkurssecurity.entities.Session;
import com.nikak.pspkurssecurity.entities.Analyst;
import com.nikak.pspkurssecurity.services.FavorService;
import com.nikak.pspkurssecurity.services.SessionService;
import com.nikak.pspkurssecurity.services.AnalystService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/public/info")
@RequiredArgsConstructor
public class PublicInfoController {

    private final FavorService favorService;
    private final SessionService sessionService;
    private final AnalystService analystService;

    @GetMapping("/analysts/certificate/{fileName}")
    public ResponseEntity<?> getAnalystCertificate(
            @PathVariable String fileName
    ) throws IOException {
        byte[] im = analystService.getCertificateImage(fileName);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(im);
    }

    @GetMapping("/analyst/{analystId}")//работает
    public ResponseEntity<List<Favor>> getListFavors(
            @PathVariable Long analystId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                //.header("Access-Control-Allow-Origin", "*")
                .body(favorService.findFavorsByAnalystId(analystId));
    }

    @GetMapping("/analysts1/{favorId}")
    public ResponseEntity<List<Analyst>> getListFavorsByRating(
            @PathVariable Long favorId,
            @RequestParam boolean order
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(analystService.getAnalystsByFavorId(favorId,order));
    }

    @GetMapping("/favors")//хз
    public ResponseEntity<?> getListFavors(
            @RequestParam String name
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                //.header("Access-Control-Allow-Origin", "*")
                .body(favorService.findFavorsWithCounts(name));
    }

    @GetMapping("/favor")//да
    public ResponseEntity<?> getListFavors(
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                //.header("Access-Control-Allow-Origin", "*")
                .body(favorService.findAllFavors());
    }

    @GetMapping("/favors/pic/{filename}")
    public ResponseEntity<?> getFavorImage(
            @PathVariable String filename
    ) throws IOException {
        byte[] im = favorService.getFavorImage(filename);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(im);
    }

    @GetMapping("/analysts/pic/{filename}")
    public ResponseEntity<?> getAnalystImage(
            @PathVariable String filename
    ) throws IOException {
        byte[] im = analystService.getAnalystImage(filename);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(im);
    }



   /* @GetMapping("subject/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Subject subject = subjectService.getSubjectById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Access-Control-Allow-Origin", "*")
                .contentType(MediaType.valueOf(subject.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + subject.getFilename() + "\"")
                .body(subject.getData());
    }*/

    @GetMapping("/analysts/{favorId}")//работает
    public ResponseEntity<List<Analyst>> getListAnalysts(
            @PathVariable Long favorId
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                //.header("Access-Control-Allow-Origin", "*")
                .body(analystService.findAnalystsByFavorId(favorId));
    }




   @GetMapping(path = "/one/{id}")//работает
   public ResponseEntity<?> getSessions(@PathVariable("id") Long id){
       return ResponseEntity.status(HttpStatus.OK)
               //.header("Access-Control-Allow-Origin", "*")
               .contentType(MediaType.APPLICATION_JSON)
               .body(sessionService.findSessionById(id));
   }

    @GetMapping(path = "/{analystId}")//работфет
    public ResponseEntity<?> getSessionsByAnalystId(@PathVariable("analystId") Long analystId){
        return ResponseEntity.status(HttpStatus.OK)
                //.header("Access-Control-Allow-Origin", "*")
                .contentType(MediaType.APPLICATION_JSON)
                .body(sessionService.findSessionsByAnalystId(analystId));
    }

    @GetMapping(path = "freeTime/{analystId}")//работает
    public ResponseEntity<?> getFreeTime(@PathVariable("analystId") Long analystId,
                                         @RequestParam String date){
        Date sqlDate = Date.valueOf(date);
        System.out.println(sqlDate);
        return ResponseEntity.status(HttpStatus.OK)
                //.header("Access-Control-Allow-Origin", "*")
                .contentType(MediaType.APPLICATION_JSON)
                .body(sessionService.findFreeTime(sqlDate,analystId));
    }

    @GetMapping(path = "popular")
    public ResponseEntity<?> getMostPopularFavors(){
        //return subjectService.getAll();

        return ResponseEntity.status(HttpStatus.OK)
                //.header("Access-Control-Allow-Origin", "*")
                .contentType(MediaType.APPLICATION_JSON)
                .body(favorService.getMostPopularFavors(PageRequest.of(0, 3)));
    }

    @GetMapping("/analysts/one/{analystId}")
    public ResponseEntity<Optional<Analyst>> getAnalystById(
            @PathVariable Long analystId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                //.header("Access-Control-Allow-Origin", "*")
                .body(analystService.findById(analystId));
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getSessions(
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                sessionService.getSessions()
        );
    }
}

