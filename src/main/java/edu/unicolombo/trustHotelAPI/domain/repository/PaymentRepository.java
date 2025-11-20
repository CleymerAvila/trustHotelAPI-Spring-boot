package edu.unicolombo.trustHotelAPI.domain.repository;

import edu.unicolombo.trustHotelAPI.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
