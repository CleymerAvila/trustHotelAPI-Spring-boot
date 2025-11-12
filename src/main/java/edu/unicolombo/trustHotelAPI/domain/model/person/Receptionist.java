package edu.unicolombo.trustHotelAPI.domain.model.person;


import edu.unicolombo.trustHotelAPI.dto.employee.UpdateReceptionistDTO;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("RECEPTIONIST")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receptionist extends Employee{
    private String mainLanguage;

    public Receptionist(String dni, String name, String phone, String email, Double salary, String workShift, String mainLanguage){
        super(dni, name, phone, email, salary, workShift);
        this.mainLanguage = mainLanguage;
    }

    public void updateData(UpdateReceptionistDTO data) {
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
        if(data != null && !data.mainLanguage().equals(this.mainLanguage)){
            this.mainLanguage = data.mainLanguage();
        }
    }
}
