package edu.unicolombo.trustHotelAPI.domain.model;

import edu.unicolombo.trustHotelAPI.domain.model.enums.EmployeeDepartment;
import edu.unicolombo.trustHotelAPI.domain.model.enums.EmployeeType;
import edu.unicolombo.trustHotelAPI.dto.employee.RegisterNewEmployeeDTO;
import edu.unicolombo.trustHotelAPI.dto.employee.UpdateEmployeeDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Employee")
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "employeeId")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String dni;
    private String email;
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private EmployeeType type;
    @Enumerated(EnumType.STRING)
    private EmployeeDepartment department;
    private String phone;
    // Relacion ManyToOne cn Hotel (para empleados normales)
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    // @OneToOne
    // @JoinColumn(name = "user_id")
    // private User user;

    public Employee(String dni, String name, String address,
                        EmployeeType type, EmployeeDepartment department, String phone, String email ) {
        this.dni= dni;
        this.name = name;
        this.address = address;
        this.type = type;
        this.department = department;
        this.phone = phone;
        this.email = email;
    }

    public Employee(RegisterNewEmployeeDTO data){
        this.dni = data.dni();
        this.name = data.name();
        this.address = data.address();
        this.type = data.type();
        this.department = data.department();
        this.phone = data.phone();
        this.email = data.email();
    }

    public void updateData(UpdateEmployeeDTO data){
        if (data.email()!= null && !data.email().equals(this.getEmail())){
            this.setEmail(data.email());
        }

        if (data.address()!=null && !data.address().equals(this.getAddress())){
            this.setAddress(data.address());
        }

        if(data.type()!=null && !data.type().equals(this.getType())){
            this.setType(data.type());
        }

        if(data.department()!= null && !data.department().equals(this.getDepartment())){
            this.setDepartment(data.department());
        }

        if(data.phone()!= null && !data.phone().equals(this.phone)){
            this.setPhone(data.phone());
        }
    }

}
