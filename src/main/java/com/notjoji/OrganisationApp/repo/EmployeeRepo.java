package com.notjoji.OrganisationApp.repo;

import com.notjoji.OrganisationApp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
