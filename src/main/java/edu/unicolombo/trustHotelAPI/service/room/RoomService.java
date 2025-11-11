package edu.unicolombo.trustHotelAPI.service.room;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import edu.unicolombo.trustHotelAPI.domain.model.Booking;
import edu.unicolombo.trustHotelAPI.domain.model.Hotel;
import edu.unicolombo.trustHotelAPI.domain.model.enums.BookingStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.RoomStatus;
import edu.unicolombo.trustHotelAPI.domain.repository.BookingRepository;
import edu.unicolombo.trustHotelAPI.dto.room.FindAvailableRoomsDTO;
import edu.unicolombo.trustHotelAPI.service.room.validations.ValidatorDates;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unicolombo.trustHotelAPI.domain.model.Room;
import edu.unicolombo.trustHotelAPI.domain.repository.HotelRepository;
import edu.unicolombo.trustHotelAPI.domain.repository.RoomRepository;
import edu.unicolombo.trustHotelAPI.dto.room.RegisterNewRoomDTO;
import edu.unicolombo.trustHotelAPI.dto.room.RoomDTO;
import edu.unicolombo.trustHotelAPI.dto.room.UpdateRoomDTO;

@Service
public class RoomService {

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    public HotelRepository hotelRepository;

    @Autowired
    public BookingRepository bookingRepository;

    @Autowired
    public List<ValidatorDates> datesValidations;

    public RoomDTO registerRoom(RegisterNewRoomDTO data){
        var hotel = hotelRepository.findById(data.hotelId())
                        .orElseThrow( () -> new EntityNotFoundException("El hotel no ha sido encontrado"));

        Room newRoom = new Room(data);
        hotel.addRoom(newRoom);
        Hotel savedHotel =  hotelRepository.save(hotel);
        var savedRoom = savedHotel.getRooms().get(savedHotel.getRooms().size()-1);
        return new RoomDTO(savedRoom);
    }

    public Room getRoomById(Long id){
        return roomRepository.getReferenceById(id);
    }

    public List<RoomDTO> getAllRooms(){
        updateRoomStatus();
        return roomRepository.findAll()
                    .stream().map(RoomDTO::new).collect(Collectors.toList());
    }

    public void deleteById(Long roomId){
        roomRepository.deleteById(roomId);
    }

    public RoomDTO updateRoom(long roomId, UpdateRoomDTO data){
        Room room = roomRepository.getReferenceById(roomId);
        room.updateData(data);
        return new RoomDTO(roomRepository.save(room));
    }

    public List<Room> findAvailableRooms(FindAvailableRoomsDTO data){
        datesValidations.forEach(val -> val.validateDate(data.startDate(), data.endDate()));
        return roomRepository.findAvailableRooms(data.startDate(), data.endDate());
    }

    @Transactional
    public void updateRoomStatus(){

        /* Some logic to update de current state of rooms daily */
        LocalDate currentDate = LocalDate.now();
        List<Booking> bookings = bookingRepository.findActiveBookings(currentDate);

        for(Booking booking: bookings){
            if (booking.getStatus().equals(BookingStatus.PENDING)){
                booking.getRoom().setCurrentState(RoomStatus.BOOKED);
            } else if( booking.getStatus().equals(BookingStatus.CONFIRMED)){
                booking.getRoom().setCurrentState(RoomStatus.OCCUPIED);
            } else {
                booking.getRoom().setCurrentState(RoomStatus.FREE);
            }
            roomRepository.save(booking.getRoom());
        }
        System.out.println("Estado de las habitaciones actualizados a la fecha " );
    }


}
