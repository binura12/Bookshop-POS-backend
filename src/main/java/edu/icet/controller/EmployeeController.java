package edu.icet.controller;

import edu.icet.dto.Employee;
import edu.icet.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@CrossOrigin
public class EmployeeController {

    final EmployeeService service;

    @PostMapping ("/add-employee")
    public void addEmployee(@RequestBody Employee employee) {
        service.addEmployee(employee);
    }

    @GetMapping("/all-active-employees")
    public List<Employee> getAllActiveEmployees() {
        return service.getAllActiveEmployees();
    }

    @GetMapping("/all-removed-employees")
    public List<Employee> getAllRemovedEmployees() {
        return service.getAllRemovedEmployees();
    }

    @DeleteMapping("/delete-employee/{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable String id) {
        boolean deactivated = service.deactivateEmployee(id);
        return ResponseEntity.ok(deactivated);
    }

    @PutMapping("/update-employee")
    public void updateEmployee(@RequestBody Employee employee){
        service.updateEmployeeById(employee);
    }
}