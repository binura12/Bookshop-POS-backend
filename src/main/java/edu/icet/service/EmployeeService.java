package edu.icet.service;

import edu.icet.dto.Employee;

import java.util.List;

public interface EmployeeService {
    void addEmployee(Employee employee);
    List<Employee> getAllActiveEmployees();
    List<Employee> getAllRemovedEmployees();
    void updateEmployeeById(Employee employee);
    boolean deactivateEmployee(String id);
}
