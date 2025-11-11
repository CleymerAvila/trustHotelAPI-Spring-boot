package edu.unicolombo.trustHotelAPI.dto.employee;

import edu.unicolombo.trustHotelAPI.domain.model.Employee;
import edu.unicolombo.trustHotelAPI.domain.model.enums.EmployeeDepartment;
import edu.unicolombo.trustHotelAPI.domain.model.enums.EmployeeType;

public record RegisterNewEmployeeDTO(Long hotelId, String dni, String name, String address,
                                     EmployeeType type, EmployeeDepartment department, String phone, String email) {

    public RegisterNewEmployeeDTO(Employee employee){
        this(employee.getHotel().getHotelId(), employee.getDni(), employee.getName(), 
        employee.getAddress(), employee.getType(), employee.getDepartment(), employee.getPhone(), employee.getEmail());
    }
}
