package com.notjoji.OrganisationApp.model.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrganisationDTO {
    private Long id;
    private String name;
    private String baseName;
    private String supervisorName;

    public String getName() {
        return name;
    }

    public String getBaseName() {
        return baseName;
    }

    public String getSupervisorName() {
        return supervisorName;
    }
}
