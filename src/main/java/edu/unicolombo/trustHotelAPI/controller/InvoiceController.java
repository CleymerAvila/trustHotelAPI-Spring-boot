package edu.unicolombo.trustHotelAPI.controller;

import edu.unicolombo.trustHotelAPI.dto.invoice.*;
import edu.unicolombo.trustHotelAPI.service.InvoiceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    @Autowired
    public InvoiceService invoiceService;

    @PostMapping("new-final")
    public ResponseEntity<InvoiceDTO> registerNewFinal(@RequestBody RegisterFinalDto data, UriComponentsBuilder uriBuilder) {
        var registeredInvoices = invoiceService.registerFinal(data.stayingId());
        URI url = uriBuilder.path("/invoice/{invoiceId}").buildAndExpand(registeredInvoices.invoiceId()).toUri();
        return ResponseEntity.created(url).body(registeredInvoices);
    }

    @PostMapping("new-initial")
    public ResponseEntity<InvoiceDTO> registerNewInitial(@RequestBody RegisterInitialDto data, UriComponentsBuilder uriComponentsBuilder){
        var registeredInvoice = invoiceService.registerInitial(data.bookingId());
        URI url = uriComponentsBuilder.path("/invoice/{invoiceId}").buildAndExpand(registeredInvoice.invoiceId()).toUri();

        return ResponseEntity.created(url).body(registeredInvoice);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable long invoiceId) {
        return ResponseEntity.ok(invoiceService.getInvoicesById(invoiceId));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<InvoiceDetailsDTO> getDetails(@PathVariable long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceDetails(id));
    }


    @DeleteMapping("/{invoiceId}")
    @Transactional
    public ResponseEntity<Void> deleteInvoice(@PathVariable long invoiceId) {
        invoiceService.deleteById(invoiceId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable long invoiceId, @RequestBody UpdateInvoiceDTO data) {
        return ResponseEntity.ok(invoiceService.updateInvoice(invoiceId,data));
    }
}
