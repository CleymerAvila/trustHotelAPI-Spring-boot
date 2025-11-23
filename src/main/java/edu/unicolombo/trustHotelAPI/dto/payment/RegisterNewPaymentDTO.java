package edu.unicolombo.trustHotelAPI.dto.payment;

import edu.unicolombo.trustHotelAPI.domain.model.Invoice;
import edu.unicolombo.trustHotelAPI.domain.model.Payment;
import edu.unicolombo.trustHotelAPI.domain.model.enums.PaymentStatus;

import java.time.LocalDateTime;

public record RegisterNewPaymentDTO(long invoiceId, String paymentMethod, double totalAmount, PaymentStatus status,
                                    LocalDateTime issueDate) {
    public RegisterNewPaymentDTO(Payment payment) {
        this(payment.getInvoice().getInvoiceId(), payment.getPaymentMethod(), payment.getTotalAmount(), payment.getStatus(),
                payment.getIssueDate());
    }
}
