package edu.unicolombo.trustHotelAPI.service;

import edu.unicolombo.trustHotelAPI.domain.model.Payment;
import edu.unicolombo.trustHotelAPI.domain.repository.PaymentRepository;
import edu.unicolombo.trustHotelAPI.dto.payment.PaymentDTO;
import edu.unicolombo.trustHotelAPI.dto.payment.RegisterNewPaymentDTO;
import edu.unicolombo.trustHotelAPI.dto.payment.UpdatePaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    public PaymentRepository paymentRepository;

    public PaymentDTO registerPayment(RegisterNewPaymentDTO data) {
        var payment = new Payment(data);
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
        var payment = paymentRepository.getReferenceById(paymentId);

        paymentRepository.delete(payment);
    }

    public PaymentDTO updatePayment(long paymentId, UpdatePaymentDTO data) {
        Payment payment = paymentRepository.getReferenceById(paymentId);
        payment.updateData(data);

        return new PaymentDTO(paymentRepository.save(payment));
    }
}
