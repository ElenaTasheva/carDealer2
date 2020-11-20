package com.springdata.course.domain.repositories;


import com.springdata.course.domain.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Set<Car> getCarByMakeOrderByModelAscTravelledDistanceDesc(String model);
    List<Car> findAll();
    Car getById(long id);


}
