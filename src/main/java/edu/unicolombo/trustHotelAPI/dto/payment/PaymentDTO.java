package edu.unicolombo.trustHotelAPI.dto.payment;

import edu.unicolombo.trustHotelAPI.domain.model.Payment;
import edu.unicolombo.trustHotelAPI.domain.model.enums.PaymentStatus;

import java.time.LocalDateTime;

public record PaymentDTO(long paymentId,
                         SimpleInvoiceDTO invoice,
                         String paymentMethod,
                         double totalAmount,
                         PaymentStatus status,
                         LocalDateTime issueDate) {
    public record SimpleInvoiceDTO(
            long invoiceId,
            String clientName
    ) {}

    public PaymentDTO(Payment payment) {
        this(
                payment.getPaymentId(),
                new SimpleInvoiceDTO(
                        payment.getInvoice().getInvoiceId(),
                        payment.getInvoice().getBooking() != null
                        ? payment.getInvoice().getBooking().getClient().getName()
                        : payment.getInvoice().getStaying().getBooking().getClient().getName()
                ),
                payment.getPaymentMethod(),
                payment.getTotalAmount(),
                payment.getStatus(),
                payment.getIssueDate()
        );
    }
}
