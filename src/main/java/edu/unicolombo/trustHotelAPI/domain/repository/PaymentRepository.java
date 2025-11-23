package edu.unicolombo.trustHotelAPI.domain.repository;

import edu.unicolombo.trustHotelAPI.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("""
    SELECT COALESCE(SUM(p.totalAmount), 0)
    FROM Payments p
    WHERE FUNCTION('MONTH', p.issueDate) = :month
      AND FUNCTION('YEAR', p.issueDate) = :year
    """)
    double sumByMonth(
            @Param("month") int month,
            @Param("year") int year
    );
}
