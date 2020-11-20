package com.springdata.course.domain.entities;


import javax.persistence.*;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity{

    private Car car;
    private Customer customer;
    private double discountPercentage;

    public Sale() {
    }

    @OneToOne
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Column(name = "discount")
    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
