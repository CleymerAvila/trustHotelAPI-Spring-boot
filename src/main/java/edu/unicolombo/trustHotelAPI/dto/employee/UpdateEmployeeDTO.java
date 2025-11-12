package edu.unicolombo.trustHotelAPI.dto.employee;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UpdatePersonnelDTO.class, name = "PERSONNEL"),
        @JsonSubTypes.Type(value = UpdateReceptionistDTO.class, name = "RECEPTIONIST"),
        @JsonSubTypes.Type(value = UpdateManagerDTO.class, name = "MANAGER")
})
public sealed interface UpdateEmployeeDTO permits UpdateReceptionistDTO, UpdateManagerDTO, UpdatePersonnelDTO {
        String email();
        String phone();
        Double  salary();
        String workShift();
        String type();
}
