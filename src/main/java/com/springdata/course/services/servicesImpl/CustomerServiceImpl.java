package com.springdata.course.services.servicesImpl;


import com.google.gson.Gson;

import com.springdata.course.constants.GlobalConstants;
import com.springdata.course.domain.dtos.CustomerExportDto;
import com.springdata.course.domain.dtos.CustomerSeedDto;
import com.springdata.course.domain.dtos.CustomerTotalSaleDto;
import com.springdata.course.domain.entities.Car;
import com.springdata.course.domain.entities.Customer;
import com.springdata.course.domain.entities.Part;
import com.springdata.course.domain.entities.Sale;
import com.springdata.course.domain.repositories.CustomerRepository;
import com.springdata.course.services.CustomerService;
import com.springdata.course.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson, CustomerRepository customerRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.customerRepository = customerRepository;
    }

    @Override
    public void seedDto() throws IOException {
        String content = String.join("", Files.readAllLines(Path.of(GlobalConstants.CUSTOMERS_FILE_PATH)));

        CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(content, CustomerSeedDto[].class);

        for (CustomerSeedDto customerSeedDto : customerSeedDtos) {
            customerRepository.saveAndFlush(this.modelMapper.map(customerSeedDto, Customer.class));

        }


    }

    @Override
    public String printCustomerOrderByBirthDateAndYoungDriver() {
        Set<Customer> ordered = this.customerRepository.getAllByOrderByBirthDateAscYoungDriverAsc();
        List<CustomerExportDto> customerExportDtos = new ArrayList<>();
        for (Customer customer : ordered) {
           customerExportDtos.add(this.modelMapper.map(customer, CustomerExportDto.class));


        }
         return  this.gson.toJson(customerExportDtos.toArray());
    }

    @Override
    public String printTotalSalesByCustomer() {
       Set<Customer> customers = this.customerRepository.findAllBySalesIsNotNull();
        List<CustomerTotalSaleDto> customersSales = new ArrayList<>();
        for (Customer customer : customers) {
        CustomerTotalSaleDto customerTotalSaleDto = new CustomerTotalSaleDto();
            customerTotalSaleDto.setFullName(customer.getName());
            int boughCars = customer.getSales().size();
            List<Part> parts = new ArrayList<>();
            customer.getSales()
                    .stream()
                    .map(Sale::getCar)
                    .map(Car::getParts)
                    .forEach(part -> part.forEach(parts::add));
          BigDecimal sum =  parts.stream().map(Part::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
          customerTotalSaleDto.setBoughtCars(boughCars);
          customerTotalSaleDto.setSpentMoney(sum);
          customersSales.add(customerTotalSaleDto);
        }
        List<CustomerTotalSaleDto> sorted = customersSales.stream().sorted(Comparator.comparing(CustomerTotalSaleDto::getSpentMoney).reversed())
//                .thenComparing(CustomerTotalSaleDto::getBoughtCars).reversed())
                .collect(Collectors.toList());
        return this.gson.toJson(sorted);
    }

}
