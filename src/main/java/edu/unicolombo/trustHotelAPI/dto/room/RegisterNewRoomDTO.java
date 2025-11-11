package edu.unicolombo.trustHotelAPI.dto.room;

import edu.unicolombo.trustHotelAPI.domain.model.enums.RoomStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.RoomType;
import edu.unicolombo.trustHotelAPI.domain.model.Room;


public record RegisterNewRoomDTO(long hotelId, RoomType type, String number,
                                 RoomStatus currentState, long floor,
                                 long capacity, Double pricePerNight) {

    public RegisterNewRoomDTO(Room room){
        this(room.getHotel().getHotelId(), room.getType(),
                room.getNumber(), room.getCurrentState(),
                room.getFloor(), room.getCapacity(), room.getPricePerNight());
    }
}
