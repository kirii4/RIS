package org.example.controllers;

import com.nikak.pspkurssecurity.dto.ResponseMessage;
import com.nikak.pspkurssecurity.entities.Favor;
import com.nikak.pspkurssecurity.entities.Analyst;
import com.nikak.pspkurssecurity.services.FavorService;
import com.nikak.pspkurssecurity.services.SessionService;
import com.nikak.pspkurssecurity.services.AnalystService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final FavorService favorService;

    private final SessionService sessionService;

    private final AnalystService analystService;



    @GetMapping
    public ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok("hello admin");
    }

    @PostMapping("/favor")
    public ResponseEntity<ResponseMessage> addFavor(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("price") Double price)
    {
        String message = "";
        try {
            message =  favorService.createFavor(file, name,price);
            System.out.println(message);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/analyst")
    public ResponseEntity<ResponseMessage> addAnalyst(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("info") String info){
        String message = "";
        try {
            message =  analystService.createAnalyst(file,name,info);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }


    @PutMapping("/favor/pic/{favorId}")
    public ResponseEntity<ResponseMessage> updateFavorPic(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable Long favorId
    ){
        String message = "";
        try {
            message =  favorService.updateFavorImage(favorId,file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping("/favor/name/{favorId}")
    public ResponseEntity<ResponseMessage> updateFavorName(
            @RequestParam(value = "name", required = false) String name,
            @PathVariable Long favorId
    ){
        String message = "";
        try {
            message =  favorService.updateFavorName(favorId, name);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not update favor name!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping("/favor/price/{favorId}")
    public ResponseEntity<ResponseMessage> updateFavorPrice(
            @RequestParam(value = "price", required = false) double price,
            @PathVariable Long favorId
    ){
        String message = "";
        System.out.println("wyte");
        try {
            message =  favorService.updateFavorPrice(favorId, price);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not update favor price!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping("/favor/{favorId}")
    public ResponseEntity<ResponseMessage> deleteFavor(
            @PathVariable Long favorId
    ){
        String message = "";
        try {
            message =  favorService.deleteFavor(favorId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not update favor name!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

   /* @PostMapping("/analyst")
    public ResponseEntity<ResponseMessage> uploadAnalystFile(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("name") String name) {
        String message = "";
        try {
            message =  analystService.addAnalyst(file,name);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }*/

    @PutMapping("/analyst/pic/{analystId}")
    public ResponseEntity<ResponseMessage> updateAnalystImage(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable Long analystId
    ){
        String message = "";
        try {
            message =  analystService.updateAnalystImage(analystId,file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping("/analyst/certificate/pic/{analystId}")
    public ResponseEntity<ResponseMessage> updateAnalystImageCertificate(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @PathVariable Long analystId
    ){
        String message = "";
        try {
            System.out.println(file+"qewr");
            message =  analystService.updateAnalystImageCertificate(analystId,file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {

            message = "Could not upload the file !";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping("/analyst/name/{analystId}")
    public ResponseEntity<ResponseMessage> updateAnalystName(
            @RequestParam(value = "name", required = false) String name,
            @PathVariable Long analystId
    ){
        String message = "";
        try {
            message =  analystService.updateAnalystName(analystId, name);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not update analyst name!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping("/analyst/info/{analystId}")
    public ResponseEntity<ResponseMessage> updateAnalystInfo(
            @RequestParam(value = "info", required = false) String info,
            @PathVariable Long analystId
    ){
        String message = "";
        try {
            message =  analystService.updateAnalystInfo(analystId, info);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not update analyst name!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @DeleteMapping("/analyst/{analystId}")
    public ResponseEntity<ResponseMessage> deleteAnalyst(
            @PathVariable Long analystId
    ){
        String message = "";
        try {
            message =  analystService.deleteAnalyst(analystId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not delete analyst!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PutMapping("assign/{analystId}/{favorId}")
    public ResponseEntity<Analyst> assignFavorToAnalyst(
            @PathVariable Long analystId,
            @PathVariable Long favorId
    ){
        return  ResponseEntity.status(HttpStatus.OK).body(
                analystService.assignFavorToAnalyst(analystId, favorId));
    }

    @DeleteMapping("assign/{analystId}/{favorId}")
    public ResponseEntity<Analyst> deleteFavorFromAnalyst(
            @PathVariable Long analystId,
            @PathVariable Long favorId
    ){
        return  ResponseEntity.status(HttpStatus.OK).body(
                analystService.deleteFavorFromAnalyst(analystId, favorId));
    }



    /*@GetMapping(path = "one/{id}")
    public ResponseEntity<ResponseMessage> getSessions(@PathVariable("id") Long id)
            throws IOException {
        String message = "";
        try {
            message =  sessionService.findSessionById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not find session";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }*/

    /*@GetMapping(path = "/session/{analyst_id}")
    public ResponseEntity<ResponseMessage> getSessionsByAnalystId(@PathVariable("analyst_id") Long analystId)  throws IOException {
        String message = "";
        try {
            message = sessionService.findSessionsByAnalystId(analystId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not find session by analyst id";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }*/

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




   /* @GetMapping(path = "/session/freeTime/{analystId}")
    public ResponseEntity<ResponseMessage> getFreeTime(
            @PathVariable("analyst_id") Long id,
            @RequestParam String date){
        String message = "";
        try {
            message =  sessionService.findFreeTime(date,id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not find dates!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }*/
   @PostMapping("/certificate/{analystId}")
   public ResponseEntity<String> addCertificate(
           @PathVariable Long analystId,
           @RequestParam(value = "file", required = false) MultipartFile file
   ){
       String message = "";
       try {
           message = analystService.addCertificate(analystId, file);
           return ResponseEntity.status(HttpStatus.OK).body(message);
       } catch (Exception e) {
           message = "Could not upload the file: " + file.getOriginalFilename() + "!";
           return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
       }
   }

    @DeleteMapping("/certificate/{analystId}")
    public ResponseEntity<String> deleteCertificate(
            @PathVariable Long analystId,
            @RequestParam(value = "certificateId", required = false) Long certificateId
    ){
        String message = "";
        try {
            message = analystService.deleteCertificate(analystId, certificateId);
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }
    @GetMapping("/favors")
    public ResponseEntity<List<Favor>> getAllFavors(){
        return ResponseEntity.ok(favorService.findAll());
    }

    @GetMapping("/analysts")
    public ResponseEntity<List<Analyst>> getAllAnalysts(){
        return ResponseEntity.ok(analystService.findAll());
    }
}
