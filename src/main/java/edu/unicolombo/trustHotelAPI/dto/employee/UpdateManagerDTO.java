package edu.unicolombo.trustHotelAPI.dto.employee;

public record UpdateManagerDTO(String email, String phone, Double  salary,
                               String workShift, String type, Double bonus) implements UpdateEmployeeDTO {
}
