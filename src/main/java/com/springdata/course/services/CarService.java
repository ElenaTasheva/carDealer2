package com.springdata.course.services;

import java.io.IOException;

public interface CarService {

   void seedCars() throws IOException;

    String printCarsByToyota();
    String printCarsWithParts();

}
