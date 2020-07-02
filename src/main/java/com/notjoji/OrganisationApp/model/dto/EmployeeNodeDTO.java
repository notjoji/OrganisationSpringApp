package com.notjoji.OrganisationApp.model.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class EmployeeNodeDTO {
    private Long id;
    private String name;
    private Long supervisorId;
    private List<EmployeeNodeDTO> children;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public void setChildren(List<EmployeeNodeDTO> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public List<EmployeeNodeDTO> getChildren() {
        return children;
    }
}
