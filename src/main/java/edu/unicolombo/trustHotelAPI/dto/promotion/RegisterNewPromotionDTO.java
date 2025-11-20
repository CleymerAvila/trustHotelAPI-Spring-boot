package edu.unicolombo.trustHotelAPI.dto.promotion;

import edu.unicolombo.trustHotelAPI.domain.model.Promotion;
import edu.unicolombo.trustHotelAPI.domain.model.enums.PromotionStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.PromotionType;

import java.time.LocalDate;

public record RegisterNewPromotionDTO(String name, String description,
                                      PromotionType type, PromotionStatus status,
                                      LocalDate startDate, LocalDate endDate, int maximumUse) {

    public RegisterNewPromotionDTO(Promotion promotion) {
        this(promotion.getName(), promotion.getDescription(), promotion.getType(),
                promotion.getStatus(), promotion.getStartDate(), promotion.getEndDate(),
                promotion.getMaximumUse());
    }
}
