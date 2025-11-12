package edu.unicolombo.trustHotelAPI.dto.client;

import edu.unicolombo.trustHotelAPI.domain.model.person.Client;

public record ClientDTO(Long clientId, String dni, String name, String address, String phone) {

    public ClientDTO(Client client) {
        this(client.getClientId(), client.getDni(), client.getName(),
                client.getAddress(), client.getPhone());
    }
}
