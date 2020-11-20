package com.springdata.course.services;


import com.springdata.course.domain.dtos.SupplierExportDto;
import com.springdata.course.domain.entities.Supplier;

import java.io.IOException;

public interface SupplierService {
    void seedSupplier() throws IOException;
    Supplier getRandomSupplier();
    String printSuppliersDoNotExportParts();

}
