package com.springdata.course.domain.repositories;


import com.springdata.course.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Set<Customer> getAllByOrderByBirthDateAscYoungDriverAsc();
    Set<Customer> findAllBySalesIsNotNull();

}
