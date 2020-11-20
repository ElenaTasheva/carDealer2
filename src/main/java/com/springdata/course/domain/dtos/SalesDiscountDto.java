package com.springdata.course.domain.dtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class SalesDiscountDto {

    @Expose
    private CarSalesDto car;
    @Expose
    private String customerName;
    @Expose
    private double discount;
    @Expose
    private BigDecimal price;
    @Expose
    private BigDecimal priceWithDiscount;

    public SalesDiscountDto() {
    }

    public CarSalesDto getCarExportDto() {
        return car;
    }

    public void setCarExportDto(CarSalesDto carSalesDto) {
        this.car = carSalesDto;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
