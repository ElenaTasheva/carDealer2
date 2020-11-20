package com.springdata.course.services.servicesImpl;


import com.google.gson.Gson;

import com.springdata.course.constants.GlobalConstants;
import com.springdata.course.domain.dtos.PartSeedDto;
import com.springdata.course.domain.entities.Part;
import com.springdata.course.domain.repositories.PartRepository;
import com.springdata.course.services.PartService;
import com.springdata.course.services.SupplierService;
import com.springdata.course.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final SupplierService supplierService;

    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.supplierService = supplierService;
    }


    @Override
    public void seedParts() throws IOException {

        String content = String.join("", Files.readAllLines(Path.of(GlobalConstants.PARTS_FILE_PATH)));
        PartSeedDto[] partSeedDtos = this.gson.fromJson(content,PartSeedDto[].class);
        for (PartSeedDto partSeedDto : partSeedDtos) {
            partSeedDto.setSupplier(this.supplierService.getRandomSupplier());
            this.partRepository.saveAndFlush(this.modelMapper.map(partSeedDto, Part.class));

        }

    }

    @Override
    public Set<Part> getRandomParts() {
        Set<Part> parts = new HashSet<>();
        Random random = new Random();
        int bound = random.nextInt(11);
        for (int i = 0; i < 10 + bound; i++) {
            long index = (long) random.nextInt((int)this.partRepository.count()) + 1;
            Part part = this.partRepository.getById(index);
            parts.add(part);
        }
        return parts;
    }
}
