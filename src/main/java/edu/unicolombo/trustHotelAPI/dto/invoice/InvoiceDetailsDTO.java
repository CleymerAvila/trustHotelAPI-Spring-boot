package edu.unicolombo.trustHotelAPI.dto.invoice;

import edu.unicolombo.trustHotelAPI.dto.client.ClientDTO;
import edu.unicolombo.trustHotelAPI.dto.payment.PaymentDTO;
import edu.unicolombo.trustHotelAPI.dto.room.RoomDTO;

import java.time.LocalDateTime;
import java.util.List;

public record InvoiceDetailsDTO(
        long invoiceId,
        LocalDateTime issueDate,
        String invoiceType,
        String status,
        String discountType,
        Double appliedDiscount,
        Double totalAmount,

        ClientDTO client,
        RoomDTO room,
        List<PaymentDTO> payments
) {}
