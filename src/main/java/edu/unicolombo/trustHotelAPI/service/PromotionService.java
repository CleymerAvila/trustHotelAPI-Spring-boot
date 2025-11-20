package edu.unicolombo.trustHotelAPI.service;

import edu.unicolombo.trustHotelAPI.domain.model.Promotion;
import edu.unicolombo.trustHotelAPI.domain.repository.PromotionRepository;
import edu.unicolombo.trustHotelAPI.dto.promotion.PromotionDTO;
import edu.unicolombo.trustHotelAPI.dto.promotion.RegisterNewPromotionDTO;
import edu.unicolombo.trustHotelAPI.dto.promotion.UpdatePromotionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    @Autowired
    public PromotionRepository promotionRepository;

    public PromotionDTO registerPromotion(RegisterNewPromotionDTO data) {
        var promotion = new Promotion(data);
        return new PromotionDTO(promotionRepository.save(promotion));
    }

    public Promotion findById(long id) {return promotionRepository.getReferenceById(id);}

    public List<PromotionDTO> getAllPromotions() {
        return promotionRepository.findAll()
                .stream().map(PromotionDTO::new).collect(Collectors.toList());
    }

    public PromotionDTO getPromotionById(long promotionId) {
        Promotion promotion = promotionRepository.getReferenceById(promotionId);
        return new PromotionDTO(promotion);
    }

    public void deleteById(long promotionId) {
        var promotion = promotionRepository.getReferenceById(promotionId);

        promotionRepository.delete(promotion);
    }

    public PromotionDTO updatePromotion(long promotionId, UpdatePromotionDTO data) {
        Promotion promotion = promotionRepository.getReferenceById(promotionId);
        promotion.updateData(data);

        return new PromotionDTO(promotionRepository.save(promotion));
    }
}
