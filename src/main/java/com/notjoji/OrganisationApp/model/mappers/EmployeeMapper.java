package com.notjoji.OrganisationApp.model.mappers;

import com.notjoji.OrganisationApp.model.dto.EmployeeDTO;
import com.notjoji.OrganisationApp.model.entity.Employee;
import com.notjoji.OrganisationApp.model.entity.Organisation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mappings({
            @Mapping(target = "id", source="employee.id"),
            @Mapping(target = "name", source = "employee.name"),
            @Mapping(target = "organisationName", source = "employee.organisation.name"),
            @Mapping(target = "supervisorName", source = "employee.supervisor.name")
    })
    EmployeeDTO employeeToDTO(Employee employee);

    @Mappings({
            @Mapping(target = "id", ignore=true),
            @Mapping(target = "name", source = "dto.name"),
            @Mapping(target = "organisation", source = "organisationArg"),
            @Mapping(target = "supervisor", source = "supervisorArg"),
            @Mapping(target = "settingOrgName", source = "dto.organisationName")
    })
    Employee employeeFromDTO(EmployeeDTO dto, Organisation organisationArg, Employee supervisorArg);
}
