package edu.unicolombo.trustHotelAPI.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.unicolombo.trustHotelAPI.domain.model.Booking;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Buscar lista de reservas activas por medio de una fecha
    @Query("""
            SELECT b FROM Booking b 
            WHERE :date 
            BETWEEN b.startDate AND b.endDate
            """)
    List<Booking> findActiveBookings(@Param("date") LocalDate date);


    @Query("""
        SELECT COUNT(b) > 0
        FROM Booking b
        WHERE b.room.roomId = :roomId
          AND NOT b.status = 'CANCELED'  
          AND (
                (b.startDate <= :endDate AND b.endDate >= :startDate)
              )
    """)
    boolean existsOverlappingBooking(@Param("roomId") Long roomId,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);


    @Query("""
            SELECT b FROM Booking b
            JOIN b.client c
            WHERE c.dni = :clientDni
            """)
    List<Booking> findBookingsByClient(@Param("clientDni") String clientDni);
}
