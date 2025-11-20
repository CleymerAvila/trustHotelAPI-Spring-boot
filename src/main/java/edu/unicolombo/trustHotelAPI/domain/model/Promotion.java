package edu.unicolombo.trustHotelAPI.domain.model;

import edu.unicolombo.trustHotelAPI.domain.model.enums.PromotionStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.PromotionType;
import edu.unicolombo.trustHotelAPI.dto.promotion.RegisterNewPromotionDTO;
import edu.unicolombo.trustHotelAPI.dto.promotion.UpdatePromotionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "promotions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "promotionId")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private PromotionType type;
    @Enumerated(EnumType.STRING)
    private PromotionStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maximumUse;

    public Promotion(RegisterNewPromotionDTO data) {
        this.name = data.name();
        this.description = data.description();
        this.type = data.type();
        this.status = data.status();
        this.startDate = data.startDate();
        this.endDate = data.endDate();
        this.maximumUse = data.maximumUse();
    }

    public void updateData(UpdatePromotionDTO data) {
        if(data.name() != null && !data.name().equals(this.name)) {
            this.name = data.name();
        }

        if(data.description() != null && !data.description().equals((this.description))){
            this.description = data.description();
        }

        if (data.type() != type) {
            this.type = data.type();
        }

        if (data.status() != status) {
            this.status = data.status();
        }

        if (data.startDate() != startDate && !data.startDate().equals(this.startDate)) {
            this.startDate = data.startDate();
        }

        if (data.endDate() != endDate && !data.endDate().equals(this.endDate)){
            this.endDate = data.endDate();
        }

        if (data.maximumUse() != maximumUse) {
            this.maximumUse = data.maximumUse();
        }
    }
}
