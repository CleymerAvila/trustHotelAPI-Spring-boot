package edu.unicolombo.trustHotelAPI.domain.repository;

import edu.unicolombo.trustHotelAPI.domain.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    //
    @Query("""
           SELECT p FROM Promotion p 
           WHERE :startDate <= p.endDate AND :endDate >= p.startDate
           AND p.status <> 'CANCELED'
    """)
    List<Promotion> findActivePromotions(@Param("startDate") LocalDate startDate,
                                         @Param("endDate") LocalDate endDate);
}