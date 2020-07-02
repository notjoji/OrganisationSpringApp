package com.notjoji.OrganisationApp.model.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class EmployeeDTO {
    private Long id;
    private String name;
    private String organisationName;
    private String supervisorName;

    public String getName() {
        return name;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public String getSupervisorName() {
        return supervisorName;
    }
}
