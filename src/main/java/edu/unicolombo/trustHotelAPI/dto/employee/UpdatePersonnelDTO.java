package edu.unicolombo.trustHotelAPI.dto.employee;

import edu.unicolombo.trustHotelAPI.domain.model.enums.EmployeeDepartment;

public record UpdatePersonnelDTO(String email, String phone, Double  salary,
                                 String workShift, String type, boolean active, EmployeeDepartment department) implements UpdateEmployeeDTO{
}
