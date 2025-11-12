package edu.unicolombo.trustHotelAPI.dto.client;

import edu.unicolombo.trustHotelAPI.domain.model.person.Client;

public record UpdateClientDTO(String email, String address, String phone) {

    public UpdateClientDTO(Client client) {
        this(client.getEmail(), client.getAddress(), client.getPhone());
    }
}
