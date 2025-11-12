package edu.unicolombo.trustHotelAPI.domain.model.person;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Person {

    private String dni;
    private String name;
    private String phone;
    private String email;
}
