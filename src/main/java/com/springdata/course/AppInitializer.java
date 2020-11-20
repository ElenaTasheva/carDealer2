package com.springdata.course;

import com.springdata.course.constants.GlobalConstants;
import com.springdata.course.services.*;

import com.springdata.course.utils.FileIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.springdata.course.constants.GlobalConstants.*;

@Component
public class AppInitializer implements CommandLineRunner {

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final FileIOUtil fileIOUtil;

    @Autowired
    public AppInitializer(SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService, FileIOUtil fileIOUtil) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;

        this.customerService = customerService;
        this.saleService = saleService;

        this.fileIOUtil = fileIOUtil;
    }


    @Override
    public void run(String... args) throws Exception {
//        this.supplierService.seedSupplier();
//        this.partService.seedParts();
//        this.carService.seedCars();
//        this.customerService.seedDto();
//        this.saleService.seedSales();

        System.out.println("All information will be displayed in files (in resource folder/output)");
        System.out.println("Please enter the number of exercise you want to be displayed.");
        System.out.println("The program will be running until you enter \"End\"");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line = reader.readLine();
        while(!line.equals("End")){
            switch (line){
                case "1":
                    this.fileIOUtil.write(this.customerService.printCustomerOrderByBirthDateAndYoungDriver()
                            , OUTPUT_1);
                    break;
                case "2":
                    this.fileIOUtil.write(this.carService.printCarsByToyota(), OUTPUT_2);
                    break;
                case "3":
                    this.fileIOUtil.write(this.supplierService.printSuppliersDoNotExportParts(), OUTPUT_3);
                    break;
                case "4":
                    this.fileIOUtil.write(this.carService.printCarsWithParts(), OUTPUT_4);
                    break;
                case "5":
                    this.fileIOUtil.write(this.customerService.printTotalSalesByCustomer(), OUTPUT_5);
                    break;
                case "6":
                    this.fileIOUtil.write(this.saleService.printAllSalesWithAndWithoutDiscount(), OUTPUT_6);
                    break;




            }
                    line = reader.readLine();
        }

    }
}
