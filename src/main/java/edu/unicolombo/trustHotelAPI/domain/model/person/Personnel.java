package edu.unicolombo.trustHotelAPI.domain.model.person;

import edu.unicolombo.trustHotelAPI.domain.model.enums.EmployeeDepartment;
import edu.unicolombo.trustHotelAPI.dto.employee.UpdatePersonnelDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("PERSONNEL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personnel extends Employee {

    private EmployeeDepartment department;

    public Personnel(String dni, String name, String phone, String email, Double salary, String workShift, boolean active, EmployeeDepartment department){
        super(dni, name, phone, email, salary, workShift, active);
        this.department = department;
    }

    public void updateData(UpdatePersonnelDTO data) {
        if(data!=null && !data.email().equals(super.getEmail())){
            super.setEmail(data.email());
        }
        if(data!=null && !data.phone().equals(super.getPhone())){
            super.setPhone(data.phone());
        }
        if(data!=null && !data.salary().equals(super.getSalary())){
            super.setSalary(data.salary());
        }
        if(data != null && !data.workShift().equals(super.getWorkShift())){
            super.setWorkShift(data.workShift());
        }
        if(data != null && !data.department().equals(this.department)){
            this.department = data.department();
        }

        if (data != null && !data.active()) {
            super.setActive(false);
        }
    }
}
