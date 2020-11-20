package com.springdata.course.services.servicesImpl;



import com.google.gson.Gson;
import com.springdata.course.domain.dtos.CarExportDto;
import com.springdata.course.domain.dtos.CarSalesDto;
import com.springdata.course.domain.dtos.SalesDiscountDto;
import com.springdata.course.domain.entities.Car;
import com.springdata.course.domain.entities.Customer;
import com.springdata.course.domain.entities.Sale;
import com.springdata.course.domain.repositories.CarRepository;
import com.springdata.course.domain.repositories.CustomerRepository;
import com.springdata.course.domain.repositories.SalesRepository;
import com.springdata.course.services.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SaleServiceImpl implements SaleService {

    private final SalesRepository salesRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public SaleServiceImpl(SalesRepository salesRepository, CarRepository carRepository, CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson) {
        this.salesRepository = salesRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;

        this.gson = gson;
    }


    @Override
    public void seedSales() {
        List<Double> discount = List.of(0.00, 0.05 , 0.10, 0.15, 0.20, 0.30, 0.40, 0.50);

        for (int i = 0; i < 3; i++) {
            Sale sale = new Sale();
            sale.setCar(gerRandomCar());
            sale.setCustomer(getRandomCustomer());
            sale.setDiscountPercentage(getRandomPercentage(discount));
            salesRepository.saveAndFlush(sale);
        }


    }

    @Override
    public String printAllSalesWithAndWithoutDiscount() {
        List<Sale> sales = this.salesRepository.findAll();
        List<SalesDiscountDto> salesDiscountDtos = new ArrayList<>();
        for (Sale sale : sales) {
            CarSalesDto carSalesDto = new CarSalesDto();
            SalesDiscountDto salesDiscountDto = new SalesDiscountDto();
            Car car = sale.getCar();
            carSalesDto = this.modelMapper.map(car, CarSalesDto.class);
            salesDiscountDto.setCarExportDto(carSalesDto);
            salesDiscountDto.setCustomerName(sale.getCustomer().getName());
            salesDiscountDto.setDiscount(sale.getDiscountPercentage());
            salesDiscountDto.setPrice(sale.getCar().carPrice());
            BigDecimal sumWithDiscount = sale.getCar().carPrice().multiply (BigDecimal.valueOf(1 - sale.getDiscountPercentage()));
            salesDiscountDto.setPriceWithDiscount(sumWithDiscount);
            salesDiscountDtos.add(salesDiscountDto);
        }
        return gson.toJson(salesDiscountDtos.toArray());

    }

    private double getRandomPercentage(List<Double> discount) {
        Random random = new Random();
        return discount.get(random.nextInt(8));
    }

    private Customer getRandomCustomer() {
        Random random = new Random();
        long id = (long)random.nextInt((int) this.customerRepository.count()) + 1;
        return this.customerRepository.getOne(id);
    }

    private Car gerRandomCar() {
        Random random = new Random();
        long id = (long)random.nextInt((int) this.carRepository.count()) + 1;
        return this.carRepository.getOne(id);
    }
}
