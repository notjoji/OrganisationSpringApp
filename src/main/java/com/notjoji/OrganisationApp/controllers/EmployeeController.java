package com.notjoji.OrganisationApp.controllers;

import com.notjoji.OrganisationApp.entity.Employee;
import com.notjoji.OrganisationApp.repo.EmployeeRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    private EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeController(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> employees = employeeRepo.findAll();

        return employees != null && !employees.isEmpty()
                ? new ResponseEntity<>(employees, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getOne(@PathVariable("id") Long id) {
        Optional<Employee> getEmployee = employeeRepo.findById(id);
        return getEmployee.isPresent()
            ? new ResponseEntity<>(getEmployee.get(), HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        Long id = employee.getSupervisorId();
        if (id != null) {
            Optional<Employee> supervisor = employeeRepo.findById(id);
            if (!supervisor.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        employeeRepo.save(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Employee employee) {
        Optional<Employee> updEmployee = employeeRepo.findById(id);
        if (!updEmployee.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        Long supervisorId = employee.getSupervisorId();
        if (supervisorId != null) {
            Optional<Employee> supervisor = employeeRepo.findById(supervisorId);
            if (!supervisor.isPresent() || supervisorId.equals(id))
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        Employee updated = updEmployee.get();
        BeanUtils.copyProperties(employee, updated, "id");
        employeeRepo.save(updated);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            Optional<Employee> delEmployee = employeeRepo.findById(id);
            if (!delEmployee.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            employeeRepo.delete(delEmployee.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }
}
