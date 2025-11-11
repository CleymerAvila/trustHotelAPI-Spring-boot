package edu.unicolombo.trustHotelAPI.service;

import edu.unicolombo.trustHotelAPI.domain.model.Client;
import edu.unicolombo.trustHotelAPI.domain.repository.ClientRepository;
import edu.unicolombo.trustHotelAPI.dto.client.ClientDTO;
import edu.unicolombo.trustHotelAPI.dto.client.RegisterNewClientDTO;
import edu.unicolombo.trustHotelAPI.dto.client.UpdateClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    public ClientRepository clientRepository;

    public Client registerClient(RegisterNewClientDTO data) {
        var client = new Client(data);
        return clientRepository.save(client);
    }

    public Client findById(long id) { return clientRepository.getReferenceById(id);}

    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll()
                .stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    public ClientDTO getClientById(long clientId) {
        Client client = clientRepository.getReferenceById(clientId);
        return new ClientDTO(client);
    }

    public void deleteById(long client) {
        clientRepository.deleteById(client);
    }

    public ClientDTO updateClient(long clientId, UpdateClientDTO data) {
        Client client = clientRepository.getReferenceById(clientId);
        client.updateData(data);
        return new ClientDTO(clientRepository.save(client));
    }

    public ClientDTO getClientByDni(String clientDni) {
        return new ClientDTO(clientRepository.getClientByDni(clientDni));
    }
}
