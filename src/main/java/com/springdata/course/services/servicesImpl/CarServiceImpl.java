package com.springdata.course.services.servicesImpl;


import com.google.gson.Gson;

import com.springdata.course.constants.GlobalConstants;
import com.springdata.course.domain.dtos.CarExportDto;
import com.springdata.course.domain.dtos.CarSeedDto;
import com.springdata.course.domain.dtos.CarsWithPartsDto;
import com.springdata.course.domain.dtos.PartExportDto;
import com.springdata.course.domain.entities.Car;
import com.springdata.course.domain.entities.Part;
import com.springdata.course.domain.repositories.CarRepository;
import com.springdata.course.services.CarService;
import com.springdata.course.services.PartService;
import com.springdata.course.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final PartService partService;

    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, PartService partService) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.partService = partService;
    }


    @Override
    @Transactional
    public void seedCars() throws IOException {
        String content = String.join("", Files.readAllLines(Path.of(GlobalConstants.CARS_FILE_PATH)));
        CarSeedDto[] carSeedDtos = this.gson.fromJson(content, CarSeedDto[].class);
        for (CarSeedDto carSeedDto : carSeedDtos) {
            Car car = modelMapper.map(carSeedDto, Car.class);
            car.setParts(this.partService.getRandomParts());
            this.carRepository.saveAndFlush(car);

        }
    }

    @Override
    public String printCarsByToyota() {

        List<CarExportDto> carExportDtoList = new ArrayList<>();
        Set<Car> carsToyota = this.carRepository.getCarByMakeOrderByModelAscTravelledDistanceDesc("Toyota");

        for (Car car : carsToyota) {
            this.modelMapper.map(car, CarExportDto.class);
            carExportDtoList.add(this.modelMapper.map(car, CarExportDto.class));
        }
        return this.gson.toJson(carExportDtoList.toArray());

    }

    @Override
    public String printCarsWithParts() {
        List<Car> cars = this.carRepository.findAll();
        List<CarsWithPartsDto> carsWithPartsDtos = new ArrayList<>();
        for (Car car : cars) {
            CarExportDto carExportDto = this.modelMapper.map(car, CarExportDto.class);
            Set<PartExportDto> partsExpo = new HashSet<>();
            for (Part parts : car.getParts()) {
                PartExportDto partExportDto = this.modelMapper.map(parts, PartExportDto.class);
                partsExpo.add(partExportDto);
            }
            CarsWithPartsDto carsWithPartsDto = new CarsWithPartsDto();
            carsWithPartsDto.setCar(carExportDto);
            carsWithPartsDto.setParts(partsExpo);
            carsWithPartsDtos.add(carsWithPartsDto);
        }
        return this.gson.toJson(carsWithPartsDtos.toArray());
    }
}
