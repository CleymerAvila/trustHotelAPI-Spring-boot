package edu.unicolombo.trustHotelAPI.dto.booking;

import java.time.LocalDate;
import java.util.List;

import edu.unicolombo.trustHotelAPI.domain.model.Booking;
import edu.unicolombo.trustHotelAPI.domain.model.enums.BookingStatus;
import edu.unicolombo.trustHotelAPI.dto.invoice.InvoiceDTO;
import edu.unicolombo.trustHotelAPI.dto.room.RoomDTO;

public record BookingDTO(Long bookingId, long clientId, long roomId,
                         LocalDate startDate, LocalDate endDate, Double advanceDeposit, BookingStatus status,
                         InvoiceDTO invoice) {

    public BookingDTO(Booking booking){
        this(booking.getBookingId(), booking.getClient().getClientId(),
                booking.getRoom().getRoomId(),
                booking.getStartDate(), booking.getEndDate(),
                booking.getAdvancePayment(), booking.getStatus(),
                booking.getInitialInvoice() !=  null
                        ? new InvoiceDTO(booking.getInitialInvoice()) : null  );
    }

}
