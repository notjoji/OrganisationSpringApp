package com.notjoji.OrganisationApp.model.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@Entity(name = "employees")
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
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;

    @Column(name = "setting_org_name")
    private String settingOrgName;

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

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public String getSettingOrgName() {
        return settingOrgName;
    }

    public void setSettingOrgName(String settingOrgName) {
        this.settingOrgName = settingOrgName;
    }
}
