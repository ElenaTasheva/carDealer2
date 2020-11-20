package com.springdata.course.domain.dtos;

import com.google.gson.annotations.Expose;
import com.springdata.course.domain.entities.Part;

import java.util.Set;

public class SupplierExportDto {

    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private boolean isImporter;
    private Set<Part> parts;
    @Expose
    private int partsCount;

    public int getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(int partsCount) {
        this.partsCount = partsCount;
    }

    public SupplierExportDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }

    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }
}
