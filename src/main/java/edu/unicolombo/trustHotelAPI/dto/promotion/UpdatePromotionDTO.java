package edu.unicolombo.trustHotelAPI.dto.promotion;

import edu.unicolombo.trustHotelAPI.domain.model.enums.PromotionStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.PromotionType;

import java.time.LocalDate;

public record UpdatePromotionDTO(String name, String description, PromotionType type,
                                 PromotionStatus status, LocalDate startDate, LocalDate endDate,
                                 int maximumUse) {
}
