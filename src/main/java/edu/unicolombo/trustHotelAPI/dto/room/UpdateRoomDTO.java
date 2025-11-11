package edu.unicolombo.trustHotelAPI.dto.room;

import edu.unicolombo.trustHotelAPI.domain.model.enums.RoomType;
import edu.unicolombo.trustHotelAPI.domain.model.enums.RoomStatus;

public record UpdateRoomDTO(RoomType type, String number,
                            RoomStatus currentState, long capacity, Double pricePerNight) {

}
