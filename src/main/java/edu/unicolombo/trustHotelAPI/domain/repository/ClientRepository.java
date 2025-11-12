package edu.unicolombo.trustHotelAPI.domain.repository;

import edu.unicolombo.trustHotelAPI.domain.model.person.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client getClientByDni(String clientDni);
}
