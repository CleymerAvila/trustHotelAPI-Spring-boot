package edu.unicolombo.trustHotelAPI.dto.staying;

import java.time.LocalDate;
import java.util.List;

import edu.unicolombo.trustHotelAPI.domain.model.Staying;
import edu.unicolombo.trustHotelAPI.domain.model.enums.StayingStatus;
import edu.unicolombo.trustHotelAPI.dto.invoice.InvoiceDTO;

public record StayingDTO(Long stayingId, Long bookingId, long roomId,
                         LocalDate checkInDate, LocalDate checkOutDate, StayingStatus status, Double totalAmount, InvoiceDTO invoice) {

    public StayingDTO(Staying staying){
        this(staying.getStayingId(), staying.getBooking().getBookingId(),
                staying.getBooking().getRoom().getRoomId(),
                staying.getCheckInDate(), staying.getCheckOutDate(),
                staying.getStatus(), staying.getTotalAmount(),
                staying.getFinalInvoice() != null
                    ? new InvoiceDTO(staying.getFinalInvoice()) : null
            );
    }
}
