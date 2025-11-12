package edu.unicolombo.trustHotelAPI.service;

import edu.unicolombo.trustHotelAPI.domain.model.person.Employee;
import edu.unicolombo.trustHotelAPI.domain.model.Hotel;
import edu.unicolombo.trustHotelAPI.domain.model.person.Manager;
import edu.unicolombo.trustHotelAPI.domain.model.person.Personnel;
import edu.unicolombo.trustHotelAPI.domain.model.person.Receptionist;
import edu.unicolombo.trustHotelAPI.domain.repository.EmployeeRepository;
import edu.unicolombo.trustHotelAPI.domain.repository.HotelRepository;
import edu.unicolombo.trustHotelAPI.dto.employee.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    public EmployeeRepository employeeRepository;

    @Autowired
    public HotelRepository hotelRepository;

    public EmployeeDTO registerEmployee(RegisterNewEmployeeDTO data){
        Hotel hotel = hotelRepository.findById(data.hotelId())
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        switch (data.type()) {
            case "PERSONNEL" -> {
                Employee newEmployee = new Personnel(data.dni(), data.name(), data.phone(), data.email(), data.salary(), data.workShift(), data.department());
                newEmployee.setHotel(hotel);
                Personnel savedEmployee = (Personnel) employeeRepository.save(newEmployee);
                return new PersonnelDTO(savedEmployee);
            }
            case "MANAGER" -> {
                Employee newEmployee = new Manager(data.dni(), data.name(), data.phone(), data.email(), data.salary(), data.workShift(), data.bonus());
                newEmployee.setHotel(hotel);
                Manager savedEmployee = (Manager) employeeRepository.save(newEmployee);
                return new ManagerDTO(savedEmployee);
            }
            case "RECEPTIONIST" -> {
                Employee newEmployee = new Receptionist(data.dni(), data.name(), data.phone(), data.email(), data.salary(), data.workShift(), data.mainLanguage());
                newEmployee.setHotel(hotel);
                Receptionist savedEmployee = (Receptionist) employeeRepository.save(newEmployee);
                return new ReceptionistDTO(savedEmployee);
            }
        }
        return null;
    }


    private EmployeeDTO mapToEmployeeDTO(Employee employee){
        if(employee instanceof  Manager manager){
            return new ManagerDTO(manager);
        } else if(employee instanceof Receptionist receptionist) {
            return new ReceptionistDTO(receptionist);
        } else if(employee instanceof Personnel personnel){
            return new PersonnelDTO(personnel);
        }
        return null;
    }
    public EmployeeDTO findById(Long id){
        Employee employee = employeeRepository.getReferenceById(id);
        return mapToEmployeeDTO(employee);
    }

    public List<EmployeeDTO> getAllEmployees(){
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToEmployeeDTO).toList();
    }

    public List<EmployeeDTO> getAllEmployeesByHotel(Long hotelId){
        var hotel = hotelRepository.getReferenceById(hotelId);
        return employeeRepository.findByHotel(hotel)
                .stream().map(this::mapToEmployeeDTO).toList();
    }

    public EmployeeDTO getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(this::mapToEmployeeDTO).orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
    }

    public void deleteById(long employeeId){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        Hotel hotel = employee.getHotel();
        hotel.getEmployees().remove(employee);
        hotelRepository.save(hotel);
        employeeRepository.delete(employee);
    }

    @Transactional
    public EmployeeDTO updateEmployee(long employeeId, UpdateEmployeeDTO data){
        Employee  employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        if(data instanceof  UpdateManagerDTO updateData){
            var manager = (Manager) employee;
            manager.updateData(updateData);
            employeeRepository.save(manager);
        } else if(data instanceof  UpdateReceptionistDTO updateData){
            var receptionist = (Receptionist) employee;
            receptionist.updateData(updateData);
            employeeRepository.save(receptionist);
        } else if(data instanceof UpdatePersonnelDTO updateData){
            var personnel = (Personnel) employee;
            personnel.updateData(updateData);
            employeeRepository.save(personnel);
        }
        return mapToEmployeeDTO(employee);
    }
}
