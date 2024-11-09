package edu.icet.service.impl;

import edu.icet.dto.Employee;
import edu.icet.entity.EmployeeEntity;
import edu.icet.repository.EmployeeRepository;
import edu.icet.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final ModelMapper mapper;

    @Override
    public void addEmployee(Employee employee) {
        //generate Id
        String nextId = generateNextEmployeeId();
        employee.setId(nextId);

        //set Assigned date
        employee.setAssignedDate(LocalDate.now());

        //set isValid to true
        employee.setValid(true);
        repository.save(mapper.map(employee, EmployeeEntity.class));
    }

    @Override
    public List<Employee> getAllActiveEmployees() {
        List<Employee> employeeArrayList = new ArrayList<>();
        repository.findAllByIsValidTrue().forEach(employeeEntity -> {
            employeeArrayList.add(mapper.map(employeeEntity, Employee.class));
        });
        return employeeArrayList;
    }

    @Override
    public List<Employee> getAllRemovedEmployees() {
        List<Employee> employeeArrayList = new ArrayList<>();
        repository.findAllByIsValidFalse().forEach(employeeEntity -> {
            employeeArrayList.add(mapper.map(employeeEntity, Employee.class));
        });
        return employeeArrayList;
    }

    @Override
    public void updateEmployeeById(Employee employee) {
        repository.save(mapper.map(employee, EmployeeEntity.class));
    }

    @Override
    public boolean deactivateEmployee(String id) {
        Optional<EmployeeEntity> optionalEmployee = repository.findById(id);
        if (optionalEmployee.isPresent()) {
            EmployeeEntity employeeEntity = optionalEmployee.get();
            employeeEntity.setResignedDate(LocalDate.now());
            employeeEntity.setValid(false);
            repository.save(employeeEntity);
            return true;
        }
        return false;
    }

    private String generateNextEmployeeId() {
        Optional<EmployeeEntity> lastEmployee = repository.findFirstByOrderByIdDesc();

        if (lastEmployee.isEmpty()) {
            return "E0001";
        }
        String lastId = lastEmployee.get().getId();
        int currentNumber = Integer.parseInt(lastId.substring(1));
        return String.format("E%04d", currentNumber + 1);
    }
}
