package com.springdata.course.services.servicesImpl;

import com.google.gson.Gson;

import com.springdata.course.constants.GlobalConstants;
import com.springdata.course.domain.dtos.SupplierExportDto;
import com.springdata.course.domain.dtos.SupplierSeedDto;
import com.springdata.course.domain.entities.Supplier;
import com.springdata.course.domain.repositories.SupplierRepository;
import com.springdata.course.services.SupplierService;
import com.springdata.course.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;


    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }


    @Override
    public void seedSupplier() throws IOException {
        String content = String.join("", Files.readAllLines(Path.of(GlobalConstants.SUPPLIERS_FILE_PATH)));
        SupplierSeedDto[] supplierSeedDtos = this.gson.fromJson(content,SupplierSeedDto[].class);
        for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
            this.supplierRepository.saveAndFlush(this.modelMapper.map(supplierSeedDto,Supplier.class));

        }


    }

    @Override
    public Supplier getRandomSupplier() {
        Random random = new Random();
        int index = random.nextInt((int) this.supplierRepository.count()) + 1;
        return this.supplierRepository.getById(index);
    }

    @Override
    public String printSuppliersDoNotExportParts() {
        List<SupplierExportDto> supplierExportDtos = new ArrayList<>();
        Set<Supplier> suppliers = this.supplierRepository.getByImporterFalse();
        for (Supplier supplier : suppliers) {
            SupplierExportDto supplierExportDto = this.modelMapper.map(supplier, SupplierExportDto.class);
            supplierExportDto.setPartsCount(supplierExportDto.getParts().size());
            supplierExportDtos.add(supplierExportDto);

        }
        return this.gson.toJson(supplierExportDtos.toArray());
    }
}
