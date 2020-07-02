package com.notjoji.OrganisationApp.model.mappers;

import com.notjoji.OrganisationApp.model.dto.EmployeeNodeDTO;
import com.notjoji.OrganisationApp.model.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeNodeMapper {
    @Mappings({
            @Mapping(target = "id", source="employee.id"),
            @Mapping(target = "name", source = "employee.name"),
            @Mapping(target = "children", source = "nodeChildren")
    })
    EmployeeNodeDTO employeeToNodeDTO(Employee employee, List<Employee> nodeChildren);

    @Mappings({
            @Mapping(target = "id", ignore=true),
            @Mapping(target = "name", source = "dto.name"),
    })
    Employee employeeFromNodeDTO(EmployeeNodeDTO dto);
}
