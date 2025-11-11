package edu.unicolombo.trustHotelAPI.dto.employee;

import edu.unicolombo.trustHotelAPI.domain.model.enums.EmployeeDepartment;
import edu.unicolombo.trustHotelAPI.domain.model.enums.EmployeeType;

public record UpdateEmployeeDTO(
        String email,
        String address,
        EmployeeType type,
        EmployeeDepartment department,
        String phone) {
}
