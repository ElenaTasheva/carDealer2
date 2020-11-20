package com.springdata.course.domain.dtos;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class CarsWithPartsDto {
    @Expose
    private CarExportDto car;
    @Expose
    private Set<PartExportDto> parts;

    public Set<PartExportDto> getParts() {
        return parts;
    }

    public void setParts(Set<PartExportDto> parts) {
        this.parts = parts;
    }

    public CarsWithPartsDto() {
    }

    public CarExportDto getCar() {
        return car;
    }

    public void setCar(CarExportDto car) {
        this.car = car;
    }


}
