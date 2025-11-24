package edu.unicolombo.trustHotelAPI.dto.invoice;

import edu.unicolombo.trustHotelAPI.domain.model.Invoice;
import edu.unicolombo.trustHotelAPI.domain.model.enums.InvoiceType;

import java.time.LocalDateTime;

public record InvoiceDTO(long invoiceId, LocalDateTime issueDate, InvoiceType type, String status,
                         Double appliedDiscount, Double finalTotal) {
    public InvoiceDTO(Invoice invoice) {
        this(invoice.getInvoiceId(),
                invoice.getIssueDate(),
                invoice.getInvoiceType(), invoice.getStatus(), invoice.getAppliedDiscount(),
                invoice.getTotalAmount());
    }
}
