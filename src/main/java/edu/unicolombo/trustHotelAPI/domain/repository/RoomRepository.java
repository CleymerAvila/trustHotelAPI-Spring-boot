package edu.unicolombo.trustHotelAPI.domain.repository;

import edu.unicolombo.trustHotelAPI.domain.model.Room;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
    SELECT r FROM Room r
    WHERE r.roomId NOT IN (
        SELECT b.room.roomId
        FROM Booking b
        WHERE (b.startDate <= :endDate AND b.endDate >= :startDate)
        AND NOT b.status = 'CANCELED'
    )
""")
    List<Room> findAvailableRooms(@Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);

    

}
