package edu.unicolombo.trustHotelAPI.service.staying;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import edu.unicolombo.trustHotelAPI.domain.model.Invoice;
import edu.unicolombo.trustHotelAPI.domain.model.Room;
import edu.unicolombo.trustHotelAPI.domain.model.enums.BookingStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.InvoiceType;
import edu.unicolombo.trustHotelAPI.domain.model.enums.RoomStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.StayingStatus;
import edu.unicolombo.trustHotelAPI.domain.repository.InvoiceRepository;
import edu.unicolombo.trustHotelAPI.infrastructure.errors.exception.BusinessLogicValidationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unicolombo.trustHotelAPI.domain.model.Staying;
import edu.unicolombo.trustHotelAPI.domain.repository.BookingRepository;
import edu.unicolombo.trustHotelAPI.domain.repository.StayingRepository;
import edu.unicolombo.trustHotelAPI.dto.staying.StayingDTO;
import edu.unicolombo.trustHotelAPI.dto.staying.UpdateStayingDTO;
import jakarta.transaction.Transactional;

@Slf4j
@Service
public class StayingService {

    @Autowired
    public BookingRepository bookingRepository;

    @Autowired
    public StayingRepository stayingRepository;

    @Autowired
    public InvoiceRepository invoiceRepository;

    @Autowired
    private CheckOutService checkOutService;

    // Metodo para encolar checkOuts
    @Transactional
    public void processCheckOutToQueue(Long stayingId){
        checkOutService.enqueueCheckOut(stayingId);
    }

    public StayingDTO checkOut(Long stayingId){
        var staying = stayingRepository.findById(stayingId)
                .orElseThrow(() -> new EntityNotFoundException("La estadia no ha sido encontrada en la base de datos"));

        if(!staying.getStatus().equals(StayingStatus.ON_PROGRESS)){
            throw new BusinessLogicValidationException("No se puede realizar el checkOut de la estadia");
        }

        Optional<Invoice> existingInvoice = invoiceRepository.findByStayingAndInvoiceType(staying, InvoiceType.FINAL);
        staying.setCheckOutDate(LocalDate.now());
        staying.setTotalAmount(calculateTotalAmountFromStaying(staying));
        System.out.println("Cuanto es el calculo total de la estadia: " + calculateTotalAmountFromStaying(staying));
        if(!existingInvoice.isPresent()){
            // actualizar valores reserva
            Invoice initialInvoice = new Invoice();
            initialInvoice.setStaying(staying);
            initialInvoice.setClient(staying.getBooking().getClient());
            initialInvoice.setInvoiceType(InvoiceType.FINAL);
            initialInvoice.setTotalAmount(calculateTotalAmountFromStaying(staying));
            initialInvoice.setIssueDate(LocalDateTime.now());
            initialInvoice.setStatus("PENDIENTE");

            staying.setFinalInvoice(initialInvoice);
        }
        stayingRepository.save(staying);
        return new StayingDTO(staying);
    }
    public void undoLastCheckOut(){
        checkOutService.undoLastCheckOut();
    }

    public List<StayingDTO> getAllStayings(){
        return stayingRepository.findAll().stream().map(StayingDTO::new).toList();
    }

    public StayingDTO getStayingById(Long stayingId) {
        var staying = stayingRepository.getReferenceById(stayingId);

        return new StayingDTO(staying);
    }


    private Double calculateTotalAmountFromStaying(Staying staying){
        final double TAXES = 0.05;
        Room room  = staying.getBooking().getRoom();

        var basePricePerNight= room.getPricePerNight();
        var daysCounts = ChronoUnit.DAYS.between(staying.getCheckOutDate(), staying.getCheckOutDate());

        return basePricePerNight * daysCounts * TAXES;
    }
}
