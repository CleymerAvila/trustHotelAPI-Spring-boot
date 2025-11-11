package edu.unicolombo.trustHotelAPI.dto.room;

import edu.unicolombo.trustHotelAPI.domain.model.Room;
import edu.unicolombo.trustHotelAPI.domain.model.enums.RoomType;
import edu.unicolombo.trustHotelAPI.domain.model.enums.RoomStatus;


public record RoomDTO(long roomId, RoomType type, String number,
                      RoomStatus currentState, long floor,
                      long capacity, Double pricePerNight) {

    public RoomDTO(Room room){
        this(room.getRoomId(), room.getType(), room.getNumber(),
                room.getCurrentState(), room.getFloor(), room.getCapacity(),
                room.getPricePerNight());
    }
}
