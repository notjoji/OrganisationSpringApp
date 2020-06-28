package com.notjoji.OrganisationApp.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Proxy;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "organisations")
@ToString(of = {"name", "baseId"})
@EqualsAndHashCode(of = {"id"})
@Proxy(lazy=false)
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
    private Long id;
    private String name;
    @Nullable
    private Long baseId;

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
}
