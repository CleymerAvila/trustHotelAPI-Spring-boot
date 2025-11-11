package edu.unicolombo.trustHotelAPI.domain.model;

import edu.unicolombo.trustHotelAPI.dto.client.RegisterNewClientDTO;
import edu.unicolombo.trustHotelAPI.dto.client.UpdateClientDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "clientId")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
    private String dni;
    private String name;
    private String email;
    private String address;
    private String phone;
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    public Client(String dni, String name, String email,String address, String phone) {
        this.dni = dni;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    public Client(RegisterNewClientDTO data) {
        this.dni = data.dni();
        this.name = data.name();
        this.email = data.email();
        this.address = data.address();
        this.phone = data.phone();
    }

    public void updateData(UpdateClientDTO data) {
        if (data.email()!=null && !data.email().equals(this.email)) {
            this.email = data.email();
        }
        if (data.phone()!=null && !data.phone().equals(this.phone)) {
            this.phone = data.phone();
        }
        if (data.address()!=null && !data.address().equals(this.address)) {
            this.address = data.address();
        }
    }
}
