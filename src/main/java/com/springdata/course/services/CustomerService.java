package com.springdata.course.services;

import java.io.IOException;

public interface CustomerService {

    void seedDto() throws IOException;
    String printCustomerOrderByBirthDateAndYoungDriver();

    String printTotalSalesByCustomer();
}
