package edu.unicolombo.trustHotelAPI.dto.payment;

import edu.unicolombo.trustHotelAPI.domain.model.Invoice;
import edu.unicolombo.trustHotelAPI.domain.model.Payment;
import edu.unicolombo.trustHotelAPI.domain.model.enums.PaymentStatus;

import java.time.LocalDateTime;

public record PaymentDTO(long paymentId, Invoice invoice, String paymentMethod, double totalAmount,
                         PaymentStatus status, LocalDateTime issueDate) {
    public PaymentDTO (Payment payment) {
        this(payment.getPaymentId(), payment.getInvoice(), payment.getPaymentMethod(),
                payment.getTotalAmount(), payment.getStatus(), payment.getIssueDate());
    }
}
