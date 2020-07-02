package com.notjoji.OrganisationApp.controllers;

import com.notjoji.OrganisationApp.model.dto.OrganisationDTO;
import com.notjoji.OrganisationApp.services.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/organisations")
public class OrganisationController {
    @Autowired
    private OrganisationService organisationService;

    @GetMapping
    public ResponseEntity<List<OrganisationDTO>> getAll() {
        List<OrganisationDTO> organisations = organisationService.getAll();

        return organisations != null && !organisations.isEmpty()
                ? new ResponseEntity<>(organisations, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganisationDTO> getById(@PathVariable("id") Long id) {
        final OrganisationDTO organisation = organisationService.getById(id);

        return organisation != null
                ? new ResponseEntity<>(organisation, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody OrganisationDTO organisationDTO) {
        return organisationService.add(organisationDTO)
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody OrganisationDTO organisationDTO) {
        return organisationService.update(id, organisationDTO)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return organisationService.delete(id)
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
