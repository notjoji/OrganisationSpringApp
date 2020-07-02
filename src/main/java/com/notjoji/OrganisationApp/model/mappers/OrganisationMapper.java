package com.notjoji.OrganisationApp.model.mappers;

import com.notjoji.OrganisationApp.model.dto.OrganisationDTO;
import com.notjoji.OrganisationApp.model.entity.Employee;
import com.notjoji.OrganisationApp.model.entity.Organisation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrganisationMapper {
    @Mappings({
            @Mapping(target = "id", source="organisation.id"),
            @Mapping(target = "name", source = "organisation.name"),
            @Mapping(target = "baseName", source = "organisation.baseOrganisation.name"),
            @Mapping(target = "supervisorName", source = "organisation.supervisor.name")
    })
    OrganisationDTO organisationToDTO(Organisation organisation);

    @Mappings({
            @Mapping(target = "id", ignore=true),
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "baseOrganisation", source = "baseOrganisationArg"),
            @Mapping(target = "supervisor", source = "supervisorArg"),
            @Mapping(target = "settingSupName", source = "dto.supervisorName"),
    })
    Organisation organisationFromDTO(OrganisationDTO dto, Organisation baseOrganisationArg, Employee supervisorArg);
}
