package edu.unicolombo.trustHotelAPI.domain.model.person;

import edu.unicolombo.trustHotelAPI.domain.model.Hotel;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "employeeId", callSuper = false)
public abstract class Employee  extends  Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private Double salary;
    private String workShift;
    // Relacion ManyToOne cn Hotel (para empleados normales)
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    // @OneToOne
    // @JoinColumn(name = "user_id")
    // private User user;

    public Employee(String dni, String name, String phone, String email, double salary, String workShift ) {
        super(dni, name, phone, email);
        this.salary = salary;
        this.workShift = workShift;
    }
//
//    public Employee(RegisterNewEmployeeDTO data){
//        this.dni = data.dni();
//        this.name = data.name();
//        this.address = data.address();
//        this.type = data.type();
//        this.department = data.department();
//        this.phone = data.phone();
//        this.email = data.email();
//    }
//
//    public void updateData(UpdateEmployeeDTO data){
//        if (data.email()!= null && !data.email().equals(this.getEmail())){
//            this.setEmail(data.email());
//        }
//
//        if (data.address()!=null && !data.address().equals(this.getAddress())){
//            this.setAddress(data.address());
//        }
//
//        if(data.type()!=null && !data.type().equals(this.getType())){
//            this.setType(data.type());
//        }
//
//        if(data.department()!= null && !data.department().equals(this.getDepartment())){
//            this.setDepartment(data.department());
//        }
//
//        if(data.phone()!= null && !data.phone().equals(this.phone)){
//            this.setPhone(data.phone());
//        }
//    }

}
