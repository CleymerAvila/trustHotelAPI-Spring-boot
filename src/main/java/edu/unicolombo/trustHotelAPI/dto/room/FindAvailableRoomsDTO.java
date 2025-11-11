package edu.unicolombo.trustHotelAPI.dto.room;

import java.time.LocalDate;

public record FindAvailableRoomsDTO(LocalDate startDate, LocalDate endDate) {
}
