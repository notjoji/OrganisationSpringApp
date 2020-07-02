package com.notjoji.OrganisationApp.controllers;

import com.notjoji.OrganisationApp.model.dto.EmployeeDTO;
import com.notjoji.OrganisationApp.model.dto.EmployeeNodeDTO;
import com.notjoji.OrganisationApp.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        List<EmployeeDTO> employees = employeeService.getAll();

        return employees != null && !employees.isEmpty()
                ? new ResponseEntity<>(employees, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getById(@PathVariable("id") Long id) {
        final EmployeeDTO employee = employeeService.getById(id);

        return employee != null
            ? new ResponseEntity<>(employee, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.add(employeeDTO)
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.update(id, employeeDTO)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return employeeService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tree/{id}")
    public ResponseEntity<EmployeeNodeDTO> getTreeByOrganisationId(@PathVariable("id") Long id) {
        EmployeeNodeDTO tree = employeeService.getTree(id);

        return tree != null
                ? new ResponseEntity<>(tree, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
