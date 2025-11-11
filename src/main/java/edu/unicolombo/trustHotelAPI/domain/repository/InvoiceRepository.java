package edu.unicolombo.trustHotelAPI.domain.repository;

import edu.unicolombo.trustHotelAPI.domain.model.Booking;
import edu.unicolombo.trustHotelAPI.domain.model.Invoice;
import edu.unicolombo.trustHotelAPI.domain.model.Staying;
import edu.unicolombo.trustHotelAPI.domain.model.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository <Invoice, Long> {

    Optional<Invoice> findByBookingAndInvoiceType(Booking booking, InvoiceType invoiceType);

    Optional<Invoice> findByStayingAndInvoiceType(Staying staying, InvoiceType invoiceType);
}
