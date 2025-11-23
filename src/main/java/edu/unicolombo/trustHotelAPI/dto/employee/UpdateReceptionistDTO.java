package edu.unicolombo.trustHotelAPI.dto.employee;

public record UpdateReceptionistDTO(String email, String phone, Double  salary,
                                   String workShift, String type, boolean active, String mainLanguage) implements UpdateEmployeeDTO {
}
