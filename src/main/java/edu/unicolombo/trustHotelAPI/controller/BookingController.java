package edu.unicolombo.trustHotelAPI.controller;

import java.net.URI;
import java.util.List;

import edu.unicolombo.trustHotelAPI.infrastructure.errors.exception.BusinessLogicValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.unicolombo.trustHotelAPI.dto.booking.BookingDTO;
import edu.unicolombo.trustHotelAPI.dto.booking.RegisterBookingDTO;
import edu.unicolombo.trustHotelAPI.dto.booking.UpdateBookingDTO;
import edu.unicolombo.trustHotelAPI.service.booking.BookingService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    public BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO> registerBooking(@RequestBody RegisterBookingDTO data, UriComponentsBuilder uriBuilder) throws BusinessLogicValidationException {
        var registeredBooking = bookingService.registerBooking(data);
        URI url = uriBuilder.path("/bookings/{bookingId}").buildAndExpand(registeredBooking.bookingId()).toUri();
        return ResponseEntity.created(url).body(registeredBooking);
    }

    @PreAuthorize("hasRole('RECEPTIONIST')")
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable Long bookingId){
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @GetMapping("/client/dni/{clientDni}")
    public ResponseEntity<List<BookingDTO>> getAllBookingsByClientDni(@PathVariable("clientDni") String clientDni){
        return ResponseEntity.ok(bookingService.getBookingsByClientDni(clientDni));
    }
    @DeleteMapping("/{bookingId}")
    @Transactional
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId){
        bookingService.deleteById(bookingId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cancel/{bookingId}")
    @Transactional
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{bookingId}")
    @Transactional
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long bookingId, @RequestBody UpdateBookingDTO data){
        return ResponseEntity.ok(bookingService.updateBooking(bookingId, data));
    }


    @GetMapping("/check-in/{bookingId}")
    @Transactional
    public ResponseEntity<BookingDTO> checkIn(@PathVariable Long bookingId){
        return ResponseEntity.ok(bookingService.checkIn(bookingId));
    }

    @GetMapping("/confirm-check-in/{bookingId}")
    @Transactional
    public ResponseEntity<BookingDTO> confirmCheckIn(@PathVariable long bookingId){
        return ResponseEntity.ok(bookingService.confirmCheckIn(bookingId));
    }
}
