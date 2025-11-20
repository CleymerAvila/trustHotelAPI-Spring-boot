package edu.unicolombo.trustHotelAPI.dto.payment;

import edu.unicolombo.trustHotelAPI.domain.model.Invoice;
import edu.unicolombo.trustHotelAPI.domain.model.Payment;
import edu.unicolombo.trustHotelAPI.domain.model.enums.PaymentStatus;

import java.time.LocalDateTime;

public record RegisterNewPaymentDTO(String paymentMethod, double totalAmount, PaymentStatus status,
                                    LocalDateTime issueDate, Invoice invoice) {
    public RegisterNewPaymentDTO(Payment payment) {
        this(payment.getPaymentMethod(), payment.getTotalAmount(), payment.getStatus(),
                payment.getIssueDate(), payment.getInvoice());
    }
}
