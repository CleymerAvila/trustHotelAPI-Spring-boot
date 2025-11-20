package edu.unicolombo.trustHotelAPI.dto.payment;

import edu.unicolombo.trustHotelAPI.domain.model.Invoice;
import edu.unicolombo.trustHotelAPI.domain.model.enums.PaymentStatus;

import java.time.LocalDateTime;

public record UpdatePaymentDTO(String paymentMethod, double totalAmount, PaymentStatus status,
                               LocalDateTime issueDate, Invoice invoice) {
}
