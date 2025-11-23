package edu.unicolombo.trustHotelAPI.dto.employee;

public record UpdateManagerDTO(String email, String phone, Double  salary,
                               String workShift, boolean active, String type, Double bonus) implements UpdateEmployeeDTO {
}
