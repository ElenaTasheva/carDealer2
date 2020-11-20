package com.springdata.course.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "supplier")
public class Supplier extends BaseEntity {

    private String name;
    private boolean isImporter;
    private Set<Part> parts;


    public Supplier() {
    }

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "is_importer")
    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }
}