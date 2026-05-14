package edu.unicolombo.trustHotelAPI.dto.booking;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterBookingDTO(
        @NotNull
        Long clientId,
        @NotNull
        Long roomId ,
        @NotNull
        LocalDate startDate,
        @NotNull
        LocalDate endDate) {
}
