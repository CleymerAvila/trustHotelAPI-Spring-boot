package edu.unicolombo.trustHotelAPI.dto.booking;

import java.time.LocalDate;

public record RegisterBookingDTO(Long clientId, Long roomId ,LocalDate startDate, LocalDate endDate) {

        

}
