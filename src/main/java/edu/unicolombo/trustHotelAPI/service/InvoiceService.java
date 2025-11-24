package edu.unicolombo.trustHotelAPI.service;

import edu.unicolombo.trustHotelAPI.domain.model.Invoice;
import edu.unicolombo.trustHotelAPI.domain.model.Room;
import edu.unicolombo.trustHotelAPI.domain.repository.InvoiceRepository;
import edu.unicolombo.trustHotelAPI.domain.repository.StayingRepository;
import edu.unicolombo.trustHotelAPI.dto.client.ClientDTO;
import edu.unicolombo.trustHotelAPI.dto.invoice.InvoiceDTO;
import edu.unicolombo.trustHotelAPI.dto.invoice.InvoiceDetailsDTO;
import edu.unicolombo.trustHotelAPI.dto.invoice.RegisterNewInvoiceDTO;
import edu.unicolombo.trustHotelAPI.dto.invoice.UpdateInvoiceDTO;
import edu.unicolombo.trustHotelAPI.dto.payment.PaymentDTO;
import edu.unicolombo.trustHotelAPI.dto.room.RoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    public InvoiceRepository invoiceRepository;

    @Autowired
    public StayingRepository stayingRepository;

    public Invoice registerInvoice(RegisterNewInvoiceDTO data) {
        var invoice = new Invoice(data);
        return invoiceRepository.save(invoice);
    }

    public Invoice findById(long id) {return invoiceRepository.getReferenceById(id);}

    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream().map(InvoiceDTO::new).collect(Collectors.toList());
    }

    public InvoiceDTO getInvoicesById(long invoiceId) {
        Invoice invoice = invoiceRepository.getReferenceById(invoiceId);
        return new InvoiceDTO(invoiceRepository.save(invoice));
    }

    public void deleteById(long invoiceId) {
        var invoice = invoiceRepository.getReferenceById(invoiceId);

        invoiceRepository.delete(invoice);
    }

    public InvoiceDTO updateInvoice(long invoiceId, UpdateInvoiceDTO data) {
        Invoice invoice = invoiceRepository.getReferenceById(invoiceId);
        invoice.updateData(data);

        return new InvoiceDTO(invoiceRepository.save(invoice));
    }

    public InvoiceDetailsDTO getInvoiceDetails(long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        // Cliente
        ClientDTO clientDTO = new ClientDTO(invoice.getClient());

        // Habitación: puede venir de Booking o Staying
        Room room = null;

        if (invoice.getBooking() != null) {
            room = invoice.getBooking().getRoom();
        } else if (invoice.getStaying() != null && invoice.getStaying().getBooking() != null) {
            room = invoice.getStaying().getBooking().getRoom();
        }


        RoomDTO roomDTO = (room != null) ? new RoomDTO(room) : null;

        // Pagos
        List<PaymentDTO> paymentDTOs = invoice.getPayments()
                .stream()
                .map(PaymentDTO::new)
                .toList();

        return new InvoiceDetailsDTO(
            invoice.getInvoiceId(),
            invoice.getIssueDate(),
            invoice.getInvoiceType().name(),
            invoice.getStatus(),
            invoice.getDiscountType(),
            invoice.getAppliedDiscount(),
            invoice.getTotalAmount(),
            clientDTO,
            roomDTO,
            paymentDTOs
        );


    }


}
