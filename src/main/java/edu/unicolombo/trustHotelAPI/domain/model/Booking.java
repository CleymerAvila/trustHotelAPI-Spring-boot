package edu.unicolombo.trustHotelAPI.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.unicolombo.trustHotelAPI.domain.model.enums.BookingStatus;
import edu.unicolombo.trustHotelAPI.domain.model.enums.InvoiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "bookingId")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double advancePayment;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    // factura inicial es opcional (0..1)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "initial_invoice", nullable = true, referencedColumnName = "invoiceId")
    private Invoice initialInvoice;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Staying staying;

    public Booking(Client client, Room room, LocalDate startDate,
                   LocalDate endDate, Double advancePayment){
        this.client = client;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.advancePayment = advancePayment;
        this.status = BookingStatus.PENDING;
    }

    public void cancel(){
        this.status = BookingStatus.CANCELED;
    }

}
