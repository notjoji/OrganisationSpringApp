package com.notjoji.OrganisationApp.repo;

import com.notjoji.OrganisationApp.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByName(String name);

    Optional<Employee> findById(Long id);
}
