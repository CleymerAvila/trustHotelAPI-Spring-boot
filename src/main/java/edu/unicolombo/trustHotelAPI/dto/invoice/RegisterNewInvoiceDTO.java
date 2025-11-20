package edu.unicolombo.trustHotelAPI.dto.invoice;

import edu.unicolombo.trustHotelAPI.domain.model.Invoice;
import edu.unicolombo.trustHotelAPI.domain.model.enums.InvoiceType;

import java.time.LocalDateTime;

public record RegisterNewInvoiceDTO(Long stayingId, String status, InvoiceType invoiceType,
                                    String discountType, Double appliedDiscount, Double totalAmount) {

    public RegisterNewInvoiceDTO(Invoice invoice) {
        this(invoice.getStaying().getStayingId(), invoice.getStatus(), invoice.getInvoiceType(),
                invoice.getDiscountType(), invoice.getAppliedDiscount(), invoice.getTotalAmount());
    }
}
