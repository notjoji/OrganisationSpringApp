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
        employeeRepo.save(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Employee employee) {
        Optional<Employee> updEmployee = employeeRepo.findById(id);
        if (!updEmployee.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        Employee updated = updEmployee.get();
        BeanUtils.copyProperties(employee, updated, "id");
        employeeRepo.save(updated);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Employee> delEmployee = employeeRepo.findById(id);
        if (!delEmployee.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        employeeRepo.delete(delEmployee.get());
        // after deleting check if it's done
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
