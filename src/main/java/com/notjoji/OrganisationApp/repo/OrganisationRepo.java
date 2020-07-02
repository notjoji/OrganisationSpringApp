package com.notjoji.OrganisationApp.repo;

import com.notjoji.OrganisationApp.model.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganisationRepo extends JpaRepository<Organisation, Long> {
    Optional<Organisation> findByName(String name);

    Optional<Organisation> findById(Long id);
}
