package edu.unicolombo.trustHotelAPI.domain.model;

import edu.unicolombo.trustHotelAPI.domain.model.enums.InvoiceType;
import edu.unicolombo.trustHotelAPI.domain.model.person.Client;
import edu.unicolombo.trustHotelAPI.dto.invoice.RegisterNewInvoiceDTO;
import edu.unicolombo.trustHotelAPI.dto.invoice.UpdateInvoiceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Invoice")
@Table(name = "invoices")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "invoiceId")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceId;
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;
    private String status;
    private LocalDateTime issueDate;
    private Double totalAmount;
    private String discountType;
    private Double appliedDiscount;
    @OneToOne(mappedBy = "initialInvoice")
    private Booking booking;
    @OneToOne(mappedBy = "finalInvoice")
    private Staying staying;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    public Invoice(Staying staying, InvoiceType invoiceType, String discountType, Double appliedDiscount, Double totalAmount) {
        this.staying = staying;
        this.invoiceType = invoiceType;
        this.discountType = discountType;
        this.appliedDiscount = appliedDiscount;
        this.totalAmount = totalAmount;
    }

    public Invoice(Booking booking, Client client, InvoiceType invoiceType,
                   String discountType, Double appliedDiscount, Double totalAmount) {
        this.booking = booking;
        this.client = client;
        this.invoiceType = invoiceType;
        this.issueDate = LocalDateTime.now();
        this.status = "PENDIENTE";
        this.discountType = discountType;
        this.appliedDiscount = appliedDiscount;
        this.totalAmount = totalAmount;
    }

    public Invoice(RegisterNewInvoiceDTO data) {
        this.invoiceType = data.invoiceType();
        this.discountType = data.discountType();
        this.appliedDiscount  = data.appliedDiscount();
        this.totalAmount = data.totalAmount();
    }
    public void updateData(UpdateInvoiceDTO data) {

//        if(data.issueDate() != null) {
//            this.issueDate = data.issueDate();
//        }
//
//        if(data.totalOfRooms() != 0) {
//            this.totalAmount = data.totalOfRooms();
//        }
//
//        if (data.finalTotal() != 0) {
//            this.finalTotal = data.finalTotal();
//        }

    }

    public void addPayment(Payment payment){
        payments.add(payment);
        payment.setInvoice(this);
    }
}
