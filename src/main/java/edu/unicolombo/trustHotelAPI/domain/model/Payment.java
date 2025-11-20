package edu.unicolombo.trustHotelAPI.domain.model;

import edu.unicolombo.trustHotelAPI.domain.model.enums.PaymentStatus;
import edu.unicolombo.trustHotelAPI.dto.payment.RegisterNewPaymentDTO;
import edu.unicolombo.trustHotelAPI.dto.payment.UpdatePaymentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Payments")
@Table(name = "payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "paymentId")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private String paymentMethod;
    private Double totalAmount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private LocalDateTime issueDate;
    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    public Payment(RegisterNewPaymentDTO data) {
        this.invoice = data.invoice();
        this.paymentMethod = data.paymentMethod();
        this.totalAmount = data.totalAmount();
        this.status = data.status();
        this.issueDate = LocalDateTime.now();
    }

    public void updateData(UpdatePaymentDTO data) {
        if(data.paymentMethod() != null && !data.paymentMethod().equals(this.paymentMethod)){
            this.paymentMethod = data.paymentMethod();
        }

        if (data.totalAmount() != totalAmount) {
            this.totalAmount = data.totalAmount();
        }

        if (data.status() != null && !data.status().equals(this.status)) {
            this.status = data.status();
        }

        if (data.issueDate() != null && !data.issueDate().equals(this.issueDate)) {
            this.issueDate = data.issueDate();
        }
    }
}
