package com.notjoji.OrganisationApp.model.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@Entity(name = "organisations")
public class Organisation {
    @Id
    @GeneratedValue(generator = "Organisation_Generator")
    @GenericGenerator(name = "Organisation_Generator",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "sequence_name", value = "Organisation_SEQ"),
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
    @JoinColumn(name = "base_id")
    private Organisation baseOrganisation;

    @OneToOne
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;

    @Column(name = "setting_sup_name")
    private String settingSupName;

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

    public Organisation getBaseOrganisation() {
        return baseOrganisation;
    }

    public void setBaseOrganisation(Organisation baseOrganisation) {
        this.baseOrganisation = baseOrganisation;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public String getSettingSupName() {
        return settingSupName;
    }

    public void setSettingSupName(String settingSupName) {
        this.settingSupName = settingSupName;
    }
}
