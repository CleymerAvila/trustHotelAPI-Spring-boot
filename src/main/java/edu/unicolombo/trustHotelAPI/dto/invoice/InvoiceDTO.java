package edu.unicolombo.trustHotelAPI.dto.invoice;

import edu.unicolombo.trustHotelAPI.domain.model.Invoice;
import edu.unicolombo.trustHotelAPI.domain.model.enums.InvoiceType;
import edu.unicolombo.trustHotelAPI.dto.payment.PaymentDTO;
import java.time.LocalDateTime;
import java.util.List;

public record InvoiceDTO(long invoiceId, LocalDateTime issueDate, InvoiceType type, String status,
                         Double appliedDiscount, Double finalTotal, List<PaymentDTO> payments) {
    public InvoiceDTO(Invoice invoice) {
        this(invoice.getInvoiceId(),
                invoice.getIssueDate(),
                invoice.getInvoiceType(), invoice.getStatus(), invoice.getAppliedDiscount(),
                invoice.getTotalAmount(),
                invoice.getPayments().stream().map(PaymentDTO::new).toList()
        );
    }
}
