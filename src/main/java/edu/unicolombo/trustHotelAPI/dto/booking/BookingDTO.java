package edu.unicolombo.trustHotelAPI.dto.booking;

import java.time.LocalDate;
import java.util.List;

import edu.unicolombo.trustHotelAPI.domain.model.Booking;
import edu.unicolombo.trustHotelAPI.domain.model.enums.BookingStatus;
import edu.unicolombo.trustHotelAPI.dto.invoice.InvoiceDTO;
import edu.unicolombo.trustHotelAPI.dto.room.RoomDTO;

public record BookingDTO(Long bookingId, String clientName, String roomNumber,
                         LocalDate startDate, LocalDate endDate, Double advanceDeposit, BookingStatus status,
                         long invoiceId) {

    public BookingDTO(Booking booking){
        this(booking.getBookingId(), booking.getClient().getName(),
                booking.getRoom().getNumber(),
                booking.getStartDate(), booking.getEndDate(),
                booking.getAdvancePayment(), booking.getStatus(),
                booking.getInitialInvoice() !=  null
                        ? booking.getInitialInvoice().getInvoiceId() : 0  );
    }

}
