package edu.unicolombo.trustHotelAPI.dto.employee;



public sealed interface EmployeeDTO permits ManagerDTO, ReceptionistDTO, PersonnelDTO {
    Long hotelId();
    Long employeeId();
    String dni();
    String name();
    String email();
    String phone();
    Double salary();
    String workShift();
    boolean active();
    String type();
}
