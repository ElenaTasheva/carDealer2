package com.springdata.course.services;


import com.springdata.course.domain.entities.Part;


import java.io.IOException;
import java.util.Set;

public interface PartService {
    void seedParts() throws IOException;
    Set<Part> getRandomParts();


}
