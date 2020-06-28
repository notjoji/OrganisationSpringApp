package com.notjoji.OrganisationApp.repo;

import com.notjoji.OrganisationApp.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationRepo extends JpaRepository<Organisation, Long> {
}
