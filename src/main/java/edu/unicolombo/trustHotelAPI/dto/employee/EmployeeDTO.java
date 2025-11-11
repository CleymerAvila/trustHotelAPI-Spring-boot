package edu.unicolombo.trustHotelAPI.dto.employee;

import edu.unicolombo.trustHotelAPI.domain.model.Employee;
import edu.unicolombo.trustHotelAPI.domain.model.enums.EmployeeDepartment;
import edu.unicolombo.trustHotelAPI.domain.model.enums.EmployeeType;

public record EmployeeDTO(long employeeId, String dni, String name,
                          String email, String address, EmployeeType type,
                          EmployeeDepartment department, String phone , String hotelName) {

    public EmployeeDTO(Employee employee){
        this(
                employee.getEmployeeId(),
                employee.getDni(),
                employee.getName(),
                employee.getEmail(),
                employee.getAddress(),
                employee.getType(),
                employee.getDepartment(),
                employee.getPhone(),
                employee.getHotel().getName()
        );
    }
}
