package edu.unicolombo.trustHotelAPI.domain.repository;

import edu.unicolombo.trustHotelAPI.domain.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
