package com.notjoji.OrganisationApp.controllers;

import com.notjoji.OrganisationApp.entity.Organisation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.notjoji.OrganisationApp.repo.OrganisationRepo;

import java.util.*;

@RestController
@RequestMapping("api/organisations")
public class OrganisationController {
    private OrganisationRepo organisationRepo;

    @Autowired
    public OrganisationController(OrganisationRepo organisationRepo) {
        this.organisationRepo = organisationRepo;
    }

    @GetMapping
    public ResponseEntity<List<Organisation>> getAll() {
        List<Organisation> organisations = organisationRepo.findAll();
        return organisations != null && !organisations.isEmpty()
                ? new ResponseEntity<>(organisations, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organisation> getOne(@PathVariable("id") Long id) {
        Optional<Organisation> getOrganisation = organisationRepo.findById(id);
        return getOrganisation.isPresent()
                ? new ResponseEntity<>(getOrganisation.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody Organisation organisation) {
        organisationRepo.save(organisation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Organisation organisation) {
        Optional<Organisation> updOrganisation = organisationRepo.findById(id);
        if (!updOrganisation.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        Organisation updated = updOrganisation.get();
        BeanUtils.copyProperties(organisation, updated, "id");
        organisationRepo.save(updated);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Organisation> delOrganisation = organisationRepo.findById(id);
        if (!delOrganisation.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        organisationRepo.delete(delOrganisation.get());
        // after deleting check if it's done
        Optional<Organisation> organisation = organisationRepo.findById(id);
        if (organisation.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
