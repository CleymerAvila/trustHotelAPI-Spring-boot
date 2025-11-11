package edu.unicolombo.trustHotelAPI.dto.invoice;

import edu.unicolombo.trustHotelAPI.domain.model.Invoice;

import java.time.LocalDateTime;

public record UpdateInvoiceDTO(LocalDateTime issueDate, Double appliedDiscount, Double finalTotal) {

    public UpdateInvoiceDTO(Invoice invoice) {
        this(invoice.getIssueDate(), invoice.getAppliedDiscount(), invoice.getTotalAmount());
    }
}
