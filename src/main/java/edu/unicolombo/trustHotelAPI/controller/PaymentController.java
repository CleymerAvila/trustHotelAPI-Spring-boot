package edu.unicolombo.trustHotelAPI.controller;

import edu.unicolombo.trustHotelAPI.dto.payment.PaymentDTO;
import edu.unicolombo.trustHotelAPI.dto.payment.RegisterNewPaymentDTO;
import edu.unicolombo.trustHotelAPI.dto.payment.UpdatePaymentDTO;
import edu.unicolombo.trustHotelAPI.service.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> registerPayment(@RequestBody RegisterNewPaymentDTO data, UriComponentsBuilder uriBuilder) {
        var registeredPayment = paymentService.registerPayment(data);
        URI url = uriBuilder.path("/payments/{paymentId}").buildAndExpand(registeredPayment.paymentId()).toUri();
        return ResponseEntity.created(url).body(registeredPayment);
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments(){
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long paymentId){
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @DeleteMapping("/{paymentId}")
    @Transactional
    public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId){
        paymentService.deleteById(paymentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{paymentId}")
    @Transactional
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable long paymentId, @RequestBody UpdatePaymentDTO data){
        return ResponseEntity.ok(paymentService.updatePayment(paymentId, data));
    }
}
