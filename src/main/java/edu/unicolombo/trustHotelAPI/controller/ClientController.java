package edu.unicolombo.trustHotelAPI.controller;

import edu.unicolombo.trustHotelAPI.dto.client.ClientDTO;
import edu.unicolombo.trustHotelAPI.dto.client.RegisterNewClientDTO;
import edu.unicolombo.trustHotelAPI.dto.client.UpdateClientDTO;
import edu.unicolombo.trustHotelAPI.service.ClientService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    @Autowired
    public ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> registerClient(@RequestBody RegisterNewClientDTO data, UriComponentsBuilder uribuilder) {
        var registeredClient = clientService.registerClient(data);
        URI url = uribuilder.path("/client/{clientId}").buildAndExpand(registeredClient.getClientId()).toUri();
        return ResponseEntity.created(url).body(new ClientDTO(registeredClient));
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable long clientId) {
        return ResponseEntity.ok(clientService.getClientById(clientId));
    }

    @GetMapping("/dni/{clientDni}")
    public ResponseEntity<ClientDTO> getClientByDni(@PathVariable String clientDni){
        return ResponseEntity.ok(clientService.getClientByDni(clientDni));
    }
    @DeleteMapping("/{clientId}")
    @Transactional
    public ResponseEntity<Void> deleteClient(@PathVariable long clientId) {
        clientService.deleteById(clientId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{clientId}")
    @Transactional
    public ResponseEntity<ClientDTO> updateClient(@PathVariable long clientId, @RequestBody UpdateClientDTO data) {
        return ResponseEntity.ok(clientService.updateClient(clientId, data));
    }
}
