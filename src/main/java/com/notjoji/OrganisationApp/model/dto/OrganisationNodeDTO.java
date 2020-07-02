package com.notjoji.OrganisationApp.model.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrganisationNodeDTO {
    private Long id;
    private String name;
    private Long baseId;
    private List<OrganisationNodeDTO> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBaseId() {
        return baseId;
    }

    public void setBaseId(Long baseId) {
        this.baseId = baseId;
    }

    public List<OrganisationNodeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<OrganisationNodeDTO> children) {
        this.children = children;
    }
}
