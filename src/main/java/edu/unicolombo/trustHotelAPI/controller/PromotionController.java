package edu.unicolombo.trustHotelAPI.controller;

import edu.unicolombo.trustHotelAPI.dto.promotion.PromotionDTO;
import edu.unicolombo.trustHotelAPI.dto.promotion.RegisterNewPromotionDTO;
import edu.unicolombo.trustHotelAPI.dto.promotion.UpdatePromotionDTO;
import edu.unicolombo.trustHotelAPI.service.PromotionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController {

    @Autowired
    PromotionService promotionService;

    @PostMapping
    public ResponseEntity<PromotionDTO> registerPromotion(@RequestBody RegisterNewPromotionDTO data, UriComponentsBuilder uriBuilder) {
        var registeredPromotion = promotionService.registerPromotion(data);
        URI url = uriBuilder.path("/promotions/{promotionId}").buildAndExpand(registeredPromotion.promotionId()).toUri();
        return ResponseEntity.created(url).body(registeredPromotion);
    }

    @GetMapping
    public ResponseEntity<List<PromotionDTO>> getAllPromotions(){
        return ResponseEntity.ok(promotionService.getAllPromotions());
    }

    @GetMapping("/{promotionId}")
    public ResponseEntity<PromotionDTO> getPromotionById(@PathVariable Long promotionId){
        return ResponseEntity.ok(promotionService.getPromotionById(promotionId));
    }

    @DeleteMapping("/{promotionId}")
    @Transactional
    public ResponseEntity<Void> deletePromotion(@PathVariable Long promotionId){
        promotionService.deleteById(promotionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{promotionId}")
    @Transactional
    public ResponseEntity<PromotionDTO> updatePromotion(@PathVariable long promotionId, @RequestBody UpdatePromotionDTO data){
        return ResponseEntity.ok(promotionService.updatePromotion(promotionId, data));
    }
}
