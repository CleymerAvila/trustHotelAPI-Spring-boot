package edu.unicolombo.trustHotelAPI.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.unicolombo.trustHotelAPI.domain.model.enums.StayingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stayings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( of = "stayingId")
public class Staying {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stayingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    @Enumerated(EnumType.STRING)
    private StayingStatus status;
    private Double totalAmount;
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;
    // factura final tambien opcional (0..1)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "final_invoice", referencedColumnName = "invoiceId", nullable = true, unique = true)
    private Invoice finalInvoice;
}
