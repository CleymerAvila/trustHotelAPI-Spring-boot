package edu.unicolombo.trustHotelAPI.domain.model;

import edu.unicolombo.trustHotelAPI.domain.model.enums.RoomStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.RoomType;
import edu.unicolombo.trustHotelAPI.dto.room.RegisterNewRoomDTO;
import edu.unicolombo.trustHotelAPI.dto.room.UpdateRoomDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "roomId")
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    private String number;
    private Double pricePerNight;
    private long floor;
    private long capacity;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    @Enumerated(EnumType.STRING)
    private RoomStatus currentState;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;


    public Room(RegisterNewRoomDTO data) {
        this.type = data.type();
        this.number = data.number();
        this.currentState = RoomStatus.FREE;
        this.floor = data.floor();
        this.capacity = data.capacity();
        this.pricePerNight = data.pricePerNight();
    }

    public void updateData(UpdateRoomDTO data) {
        if (data.type()!=null && !data.type().equals(this.type)) {
            this.type = data.type();
        }

        if(data.number()!=null && !data.number().equals(this.number)){
            this.number = data.number();
        }

        if (data.currentState()!=null && !data.currentState().equals(this.currentState)) {
            this.currentState = data.currentState();
        }

        if(data.capacity()!=this.capacity){
            this.capacity = data.capacity();
        }

        if(data.pricePerNight()!= null && !data.pricePerNight().equals(this.pricePerNight)){
            this.pricePerNight = data.pricePerNight();
        }
    }
}
