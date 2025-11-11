package edu.unicolombo.trustHotelAPI.service.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import edu.unicolombo.trustHotelAPI.domain.model.Invoice;
import edu.unicolombo.trustHotelAPI.domain.model.Staying;
import edu.unicolombo.trustHotelAPI.domain.model.enums.BookingStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.InvoiceType;
import edu.unicolombo.trustHotelAPI.domain.model.enums.StayingStatus;
import edu.unicolombo.trustHotelAPI.domain.repository.*;
import edu.unicolombo.trustHotelAPI.infrastructure.errors.exception.BusinessLogicValidationException;
import edu.unicolombo.trustHotelAPI.service.InvoiceService;
import edu.unicolombo.trustHotelAPI.service.room.validations.ValidatorDates;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unicolombo.trustHotelAPI.domain.model.Booking;
import edu.unicolombo.trustHotelAPI.domain.model.Room;
import edu.unicolombo.trustHotelAPI.dto.booking.BookingDTO;
import edu.unicolombo.trustHotelAPI.dto.booking.RegisterBookingDTO;
import edu.unicolombo.trustHotelAPI.dto.booking.UpdateBookingDTO;
import jakarta.transaction.Transactional;

@Service
public class BookingService {

    @Autowired
    public BookingRepository bookingRepository;

    @Autowired
    public RoomRepository roomRepository;

    @Autowired 
    public ClientRepository clientRepository;

    @Autowired
    public StayingRepository stayingRepository;

    @Autowired
    public InvoiceRepository invoiceRepository;

    @Autowired
    public List<ValidatorDates> validatorDates;

    @Transactional
    public BookingDTO registerBooking(RegisterBookingDTO data) throws BusinessLogicValidationException{
        validatorDates.forEach(v -> v.validateDate(data.startDate(), data.endDate()));
        boolean exits = bookingRepository.existsOverlappingBooking(data.roomId(), data.startDate(), data.endDate());

        if(exits){
            throw new BusinessLogicValidationException("La habitacion ya se encuentra reservada para ese rango de fecha");
        }

        var client = clientRepository.findById(data.clientId())
                .orElseThrow( () -> new EntityNotFoundException("El cliente no fue encontrado"));
        var room = roomRepository.findById(data.roomId())
                .orElseThrow(() -> new EntityNotFoundException("La habitacion no fue encontrada"));

        var advancePayment = calculateAdvancePayment(room, data.startDate(), data.endDate());
        var booking = new Booking(client, room, data.startDate(), data.endDate(), advancePayment);
        return new BookingDTO(bookingRepository.save(booking));
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(BookingDTO::new).toList();
    }

    public BookingDTO getBookingById(Long bookingId) {
        return new BookingDTO(bookingRepository.getReferenceById(bookingId));
    }

    public void deleteById(Long bookingId) {
        Booking booking = bookingRepository.getReferenceById(bookingId);
       bookingRepository.deleteById(bookingId);
    }

    public void cancelBooking(Long bookingId){
        var booking = bookingRepository.getReferenceById(bookingId);
        if(!booking.getStatus().equals(BookingStatus.PENDING)){
            throw new BusinessLogicValidationException("La reserva no puede ser cancelada ya que no se encuentra pendiente");
        }
        booking.cancel();
        bookingRepository.save(booking);
    }

    public BookingDTO updateBooking(Long bookingId, UpdateBookingDTO data) {
        validatorDates.forEach(v -> v.validateDate(data.startDate(), data.endDate()));
        var exist = bookingRepository.existsOverlappingBooking(data.roomId(), data.startDate(), data.endDate());

        if(exist){
            throw new BusinessLogicValidationException("Ya se encuentra una reserva con esa habitacion en esas fechas");
        }
        var booking = bookingRepository.getReferenceById(bookingId);
        if(!booking.getStatus().equals(BookingStatus.PENDING)){
            throw new BusinessLogicValidationException("No es posible actualizar la reserva porque se encuentra " + booking.getStatus().getDisplayName());
        }
        System.out.println("El estado de la reserva es : " + booking.getStatus());
        var room = roomRepository.getReferenceById(data.roomId());
        booking.setRoom(room);
        booking.setStartDate(data.startDate());
        booking.setEndDate(data.endDate());
        var advancePayment = calculateAdvancePayment(room, data.startDate(), data.endDate());
        booking.setAdvancePayment(advancePayment);
        return new BookingDTO(bookingRepository.save(booking));
    }


    public BookingDTO checkIn(Long bookingId){
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("La reserva no ha sido encontrada en la base de datos"));

        if(!booking.getStatus().equals(BookingStatus.PENDING)){
            throw new BusinessLogicValidationException("No se puede realizar el check-in de la reserva "+ bookingId +
                    " por que no se encuentra pendiente");
        }

        Optional<Invoice> existingInvoice = invoiceRepository.findByBookingAndInvoiceType(booking, InvoiceType.INITIAL);

        if (!existingInvoice.isPresent()){
            // actualizar valores reserva
            Invoice initialInvoice = new Invoice();
            initialInvoice.setBooking(booking);
            initialInvoice.setClient(booking.getClient());
            initialInvoice.setInvoiceType(InvoiceType.INITIAL);
            initialInvoice.setTotalAmount(booking.getAdvancePayment());
            initialInvoice.setIssueDate(LocalDateTime.now());
            initialInvoice.setStatus("PENDIENTE");

            booking.setInitialInvoice(initialInvoice);
        }
        bookingRepository.save(booking);
        return new BookingDTO(booking);
    }

    public BookingDTO confirmCheckIn(Long bookingId){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("La reserva no existe en la base de datos"));

        if(booking.getInitialInvoice()==null){
            throw new BusinessLogicValidationException("La reserva aún no cuenta con una factura por favor primero genere una para confirma checkIn");
        }
        Invoice invoice = invoiceRepository.getReferenceById(booking.getInitialInvoice().getInvoiceId());

        if(invoice.getBooking()==null){
            throw new BusinessLogicValidationException("La factura no pertenece a una reserva");
        }
        // inicializar valores estadia
        LocalDate checkInDate = LocalDate.now();

        if(checkInDate.isBefore(booking.getStartDate()) || checkInDate.isAfter(booking.getEndDate())){
            throw new BusinessLogicValidationException("No es posible realizar el check-in fuera de las fechas establecidas de la reserva: " + booking.getBookingId());
        }
        // TODO: implements the payments for the initial invoices
        invoice.setStatus("PAGADO");
        booking.setStatus(BookingStatus.CONFIRMED);

        bookingRepository.save(booking);
        invoiceRepository.save(invoice);
        Staying staying = new Staying();
        staying.setBooking(booking);
        staying.setCheckInDate(checkInDate);
        staying.setStatus(StayingStatus.ON_PROGRESS);
        stayingRepository.save(staying);

        return new BookingDTO(booking);
    }
    public List<BookingDTO> getBookingsByClientDni(String clientDni) {
        return bookingRepository.findBookingsByClient(clientDni).stream().map(BookingDTO::new).toList();
    }

    private Double calculateAdvancePayment(Room room, LocalDate startDate, LocalDate endDate){
        // calcular el pago adelantado por la reserva de la hab
        final double TAXES = 0.10;
        double total = 0D;
        Long bookingDays = ChronoUnit.DAYS.between(startDate, endDate);
        total = bookingDays * room.getPricePerNight() * TAXES;
        return total;
    }
}
