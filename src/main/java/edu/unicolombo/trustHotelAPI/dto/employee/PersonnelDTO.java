package edu.unicolombo.trustHotelAPI.dto.employee;

import edu.unicolombo.trustHotelAPI.domain.model.enums.EmployeeDepartment;
import edu.unicolombo.trustHotelAPI.domain.model.person.Personnel;

public record PersonnelDTO(Long hotelId, Long employeeId, String dni, String name,
                           String email, String phone, Double salary,
                           String workShift, boolean active, String type,  EmployeeDepartment department) implements EmployeeDTO {

    public PersonnelDTO(Personnel personnel){
        this(personnel.getHotel().getHotelId(), personnel.getEmployeeId(), personnel.getDni(), personnel.getName(),
                personnel.getEmail(), personnel.getPhone(), personnel.getSalary(),
                personnel.getWorkShift(), personnel.isActive(), "PERSONNEL", personnel.getDepartment());
    }
}
