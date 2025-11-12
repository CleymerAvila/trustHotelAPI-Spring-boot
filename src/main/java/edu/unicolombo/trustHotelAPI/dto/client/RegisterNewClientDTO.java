package edu.unicolombo.trustHotelAPI.dto.client;

import edu.unicolombo.trustHotelAPI.domain.model.person.Client;

public record RegisterNewClientDTO(String dni, String name, String address,
                                   String email, String phone ) {

    public RegisterNewClientDTO(Client client) {
        this(client.getDni(), client.getName(), client.getAddress(),
                client.getEmail(), client.getPhone());
    }
}
