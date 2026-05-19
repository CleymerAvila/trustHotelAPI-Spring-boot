package edu.unicolombo.trustHotelAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.unicolombo.trustHotelAPI.dto.staying.StayingDTO;
import edu.unicolombo.trustHotelAPI.service.staying.StayingService;

@RestController
@RequestMapping("/api/v1/stayings")
public class StayingController {

    @Autowired
    public StayingService stayingService;

    @GetMapping
    public ResponseEntity<List<StayingDTO>> getAllStayings(){
        return ResponseEntity.ok(stayingService.getAllStayings());
    }

    @GetMapping("/{stayingId}")
    public ResponseEntity<StayingDTO> getStayingById(@PathVariable Long stayingId){
        var staying = stayingService.getStayingById(stayingId);
        return ResponseEntity.ok(staying);
    }

    @GetMapping("/check-out/{stayingId}")
    public ResponseEntity<String> enqueueCheckOut(@PathVariable Long stayingId){
        stayingService.processCheckOutToQueue(stayingId);
        return ResponseEntity.accepted().body("Check-out en cola para procesamiento");
    }

//    @GetMapping("/check-out/{stayingId}")
//    public ResponseEntity<StayingDTO> checkOutProcess(@PathVariable Long stayingId){
//        return ResponseEntity.ok(stayingService.checkOut(stayingId));
//    }

    @GetMapping("/check-out/undo")
    public ResponseEntity<String> undoLastCheckOut(){
        stayingService.undoLastCheckOut();
        return ResponseEntity.ok("Ultimo check-out revertido");
    }

}
