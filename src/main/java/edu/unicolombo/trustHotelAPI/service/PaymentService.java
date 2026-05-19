package edu.unicolombo.trustHotelAPI.service;

import edu.unicolombo.trustHotelAPI.domain.model.Payment;
import edu.unicolombo.trustHotelAPI.domain.model.enums.InvoiceType;
import edu.unicolombo.trustHotelAPI.domain.repository.InvoiceRepository;
import edu.unicolombo.trustHotelAPI.domain.repository.PaymentRepository;
import edu.unicolombo.trustHotelAPI.dto.payment.PaymentDTO;
import edu.unicolombo.trustHotelAPI.dto.payment.RegisterNewPaymentDTO;
import edu.unicolombo.trustHotelAPI.dto.payment.UpdatePaymentDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    public PaymentRepository paymentRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public PaymentDTO registerPayment(RegisterNewPaymentDTO data) {
        var invoice = invoiceRepository.findById(data.invoiceId())
                .orElseThrow(() -> new EntityNotFoundException("No existe la factura referenciada"));

        invoice.setClient(invoice.getBooking() != null ? invoice.getBooking().getClient() : invoice.getStaying().getBooking().getClient() );
        var payment = new Payment(data);
        invoice.addPayment(payment);
        payment.setInvoice(invoice);
        Double totalPaid = invoice.getPayments().stream().mapToDouble(Payment::getTotalAmount).sum();
        System.out.println("Total Pagado: " + totalPaid);
        if (invoice.getInvoiceType().equals(InvoiceType.INITIAL)) {
            if(Double.compare(totalPaid, invoice.getBooking().getAdvancePayment()) >= 0){
                System.out.println("Total pagado: " +  totalPaid + " es igual o mayor a "
                        + invoice.getBooking().getAdvancePayment() + " ( " + (Double.compare(totalPaid, invoice.getBooking().getAdvancePayment()) >= 0));
                invoice.setStatus("FULLY_PAID");
            } else {
                invoice.setStatus("PARTIALLY_PAID");
            }
        } else {
            if (totalPaid.equals(invoice.getTotalAmount())){
                invoice.setStatus("FULLY_PAID");
            } else {
                invoice.setStatus("PARTIALLY_PAID");
            }
        }
        return new PaymentDTO(paymentRepository.save(payment));
    }

    public Payment findById(long id) {return paymentRepository.getReferenceById(id);}

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream().map(PaymentDTO::new).collect(Collectors.toList());
    }

    public PaymentDTO getPaymentById(long paymentId) {
        Payment payment = paymentRepository.getReferenceById(paymentId);
        return new PaymentDTO(payment);
    }

    public void deleteById(long paymentId) {
        var payment = paymentRepository.findById(paymentId).orElseThrow(()-> new EntityNotFoundException("Pago no encontrado"));
        paymentRepository.delete(payment);
    }

    public PaymentDTO updatePayment(long paymentId, UpdatePaymentDTO data) {
        Payment payment = paymentRepository.getReferenceById(paymentId);
        payment.updateData(data);

        return new PaymentDTO(paymentRepository.save(payment));
    }
}
