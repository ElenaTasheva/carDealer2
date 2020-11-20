package com.springdata.course.domain.repositories;

import com.springdata.course.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Supplier getById(long id);
    Set<Supplier> getByImporterFalse();
}
