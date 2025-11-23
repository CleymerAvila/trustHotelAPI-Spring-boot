package edu.unicolombo.trustHotelAPI.domain.model.person;

import edu.unicolombo.trustHotelAPI.dto.employee.UpdateManagerDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("MANAGER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manager extends Employee{
    private Double bonus;


    public Manager(String dni, String name, String phone, String email, Double salary, String workShift, Double bonus){
        super(dni, name, phone, email, salary, workShift);
        this.bonus = bonus;
    }

    public void updateData(UpdateManagerDTO data){
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
        if(data != null && !data.bonus().equals(this.bonus)) {
            this.bonus = data.bonus();
        }
        if (data != null && !data.active()) {
            super.setActive(false);
        }
    }
}
