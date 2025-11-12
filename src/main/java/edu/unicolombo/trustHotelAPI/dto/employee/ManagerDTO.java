package edu.unicolombo.trustHotelAPI.dto.employee;

import edu.unicolombo.trustHotelAPI.domain.model.person.Manager;

public record ManagerDTO(Long hotelId, Long employeeId, String dni, String name,
                         String email, String phone, Double salary,
                         String workShift, String type,  Double bonus) implements EmployeeDTO {

    public ManagerDTO(Manager manager){
        this(manager.getHotel().getHotelId(), manager.getEmployeeId(),  manager.getDni(), manager.getName(),
                    manager.getEmail(), manager.getPhone(), manager.getSalary(),
                            manager.getWorkShift(), "MANAGER", manager.getBonus());
    }
}
