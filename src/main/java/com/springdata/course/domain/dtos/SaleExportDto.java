package com.springdata.course.domain.dtos;


import com.google.gson.annotations.Expose;


public class SaleExportDto {

    @Expose
    private long id;
    @Expose
    private double discountPercentage;

    public SaleExportDto(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
