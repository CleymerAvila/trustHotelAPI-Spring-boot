package edu.unicolombo.trustHotelAPI.domain.model.person;

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
@EqualsAndHashCode(of = "clientId", callSuper = false)
public class Client extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;
    private String address;
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    public Client(String dni, String name, String phone, String email,String address ) {
        super(dni, name, phone, email);
        this.address = address;
    }

    public Client(RegisterNewClientDTO data) {
        super(data.dni(), data.name(), data.phone(), data.email());
        this.address = data.address();
    }

    public void updateData(UpdateClientDTO data) {
        if (data.email()!=null && !data.email().equals(super.getEmail())) {
            super.setEmail(data.email());
        }
        if (data.phone()!=null && !data.phone().equals(super.getPhone())) {
            super.setPhone(data.phone());
        }
        if (data.address()!=null && !data.address().equals(this.address)) {
            this.address = data.address();
        }
    }
}
