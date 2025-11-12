package edu.unicolombo.trustHotelAPI.dto.employee;

import edu.unicolombo.trustHotelAPI.domain.model.enums.EmployeeDepartment;
import edu.unicolombo.trustHotelAPI.domain.model.person.Manager;
import edu.unicolombo.trustHotelAPI.domain.model.person.Personnel;
import edu.unicolombo.trustHotelAPI.domain.model.person.Receptionist;

public record RegisterNewEmployeeDTO(Long hotelId, String dni, String name,
                                     String email, String phone,
                                     Double salary, String workShift, String type,
                                     Double bonus, String mainLanguage, EmployeeDepartment department) {

    public RegisterNewEmployeeDTO(Manager manager){
        this(manager.getHotel().getHotelId(), manager.getDni(), manager.getName(),
                manager.getEmail(), manager.getPhone(), manager.getSalary(),
                manager.getWorkShift(), "MANAGER", manager.getBonus(), null, null );
    }

    public RegisterNewEmployeeDTO(Receptionist receptionist){
        this(receptionist.getHotel().getHotelId(), receptionist.getDni(), receptionist.getName(),
                receptionist.getEmail(), receptionist.getPhone(), receptionist.getSalary(),
                receptionist.getWorkShift(), "RECEPCIONIST", null, receptionist.getMainLanguage(), null );
    }

    public RegisterNewEmployeeDTO(Personnel personnel){
        this(personnel.getHotel().getHotelId(), personnel.getDni(), personnel.getName(),
                personnel.getEmail(), personnel.getPhone(), personnel.getSalary(),
                personnel.getWorkShift(), "PERSONNEL", null, null, personnel.getDepartment() );
    }


}
