package edu.unicolombo.trustHotelAPI.dto.employee;

import edu.unicolombo.trustHotelAPI.domain.model.person.Receptionist;

public record ReceptionistDTO(Long hotelId, Long employeeId, String dni, String name,
                              String email, String phone, Double salary,
                              String workShift, String type, String mainLanguage) implements EmployeeDTO {

    public ReceptionistDTO(Receptionist receptionist){
        this(receptionist.getHotel().getHotelId(), receptionist.getEmployeeId(), receptionist.getDni(), receptionist.getName(),
                receptionist.getEmail(), receptionist.getPhone(), receptionist.getSalary(),
                receptionist.getWorkShift(), "RECEPTIONIST", receptionist.getMainLanguage());
    }
}