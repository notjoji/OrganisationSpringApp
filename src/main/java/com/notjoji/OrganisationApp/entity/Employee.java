package com.notjoji.OrganisationApp.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Proxy;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@ToString(of = {"name", "organisationId", "supervisorId"})
@EqualsAndHashCode(of = {"id"})
@Proxy(lazy=false)
public class Employee {
    @Id
    @GeneratedValue(generator = "Employee_Generator")
    @GenericGenerator(name = "Employee_Generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "sequence_name", value = "Employee_SEQ"),
            @Parameter(name = "optimizer", value = "hilo"),
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")
        }
    )
    private Long id;
    private String name;
    private Long organisationId;
    @Nullable
    private Long supervisorId;

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

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    @Nullable
    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(@Nullable Long supervisorId) {
        this.supervisorId = supervisorId;
    }
}
