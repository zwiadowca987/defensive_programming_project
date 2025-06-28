package com.example.dpp;

import com.example.dpp.model.Role;
import com.example.dpp.model.api.AddressInfo;
import com.example.dpp.model.api.auth.RegisterUser;
import com.example.dpp.model.api.auth.RoleAssignment;
import com.example.dpp.model.api.customer.NewCustomer;
import com.example.dpp.model.api.products.ProductCreation;
import com.example.dpp.model.api.warehouses.WarehouseCreation;
import com.example.dpp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Profile("dev")
public class Initializer implements CommandLineRunner {

    /**
     * Inicjalizacja dzaiła wyłącznie dla profilu "dev"
     * wymaga dodania flagi -Dspring.profiles.active=dev
     * W InteliJ:
     * 1. Otwórz Run > Edit Configurations (lub kliknij w menu rozwijane obok przycisku „Run” ▶️ i wybierz „Edit Configurations…”).
     * 2. Wybierz swoją konfigurację Spring Boot (albo kliknij ➕ by dodać Spring Boot > Application).
     * 3. W polu VM options wpisz:
     * -Dspring.profiles.active=dev
     * 4. Zapisz i uruchom aplikację jak zwykle (Shift + F10).
     */

    private final CustomerServiceImpl customerService;
    private final ProductServiceImpl productService;
    private final UserServiceImpl userService;
    private final PurchaseServiceImpl purchaseService;
    private final WarehouseServiceImpl warehouseService;


    @Autowired
    public Initializer(CustomerServiceImpl customerService,
                       ProductServiceImpl productService,
                       UserServiceImpl userService,
                       PurchaseServiceImpl purchaseService,
                       WarehouseServiceImpl warehouseService) {
        this.customerService = customerService;
        this.productService = productService;
        this.userService = userService;
        this.purchaseService = purchaseService;
        this.warehouseService = warehouseService;
    }

    public void initialize() {
        var userAdam =
                new RegisterUser(
                        "adam1",
                        "Adam",
                        "Adamczyk",
                        "adam@gmail.com",
                        "Qwerty123456#");

        var userKarol = new RegisterUser(
                "karol1",
                "Karol",
                "Krawiec",
                "karolek@example.com",
                "Qwerty123456#"
        );

        userService.register(userAdam);
        userService.register(userKarol);

        var idAdam = userService.getUserInfoByEmail(userAdam.getEmail()).getId();
        var idKarol = userService.getUserInfoByEmail(userKarol.getEmail()).getId();

        userService.setRole(new RoleAssignment(idAdam, Role.USER));
        userService.setRole(new RoleAssignment(idKarol, Role.ADMINISTRATOR));

        var customerAnna = new NewCustomer(
                "Anna",
                "Kowalska",
                new AddressInfo(
                        "Polska",
                        "Kielce",
                        "25-314",
                        "Al. Tysiąclecia Państwa Polskiego",
                        "7"),
                "anna@example.com",
                "+48123456789");

        var customerBarbara = new NewCustomer(
                "Barbara",
                "Brzoza",
                new AddressInfo(
                        "Polska",
                        "Wrocław",
                        "50-300",
                        "Fryderyka Joliot-Curie ",
                        "15"),
                "basia@example.com",
                "+48987654321");

        var customerCecylia = new NewCustomer(
                "Cecylia",
                "Cokół",
                new AddressInfo(
                        "Polska",
                        "Warszawa",
                        "02-097",
                        "Stefana Banacha",
                        "2"),
                "caci@example.com",
                "+48666666666");

        customerService.createCustomer(customerAnna);
        customerService.createCustomer(customerBarbara);
        customerService.createCustomer(customerCecylia);

        var idAnna =
                customerService.getCustomerByEmail(customerAnna.getCustomerEmail()).getId();
        var idBarbara =
                customerService.getCustomerByEmail(customerBarbara.getCustomerEmail()).getId();
        var idCecylia =
                customerService.getCustomerByEmail(customerCecylia.getCustomerEmail()).getId();

        var mainWarehouse = new WarehouseCreation("Main", new AddressInfo(
                "Polska",
                "Kielce",
                "25-315",
                "aleja IX Wieków Kielc",
                "19a"
        ));
        var smallWarehouse = new WarehouseCreation("Small", new AddressInfo(
                "Polska",
                "Kielce",
                "25-315",
                "aleja IX Wieków Kielc",
                "19"
        ));

        warehouseService.createWarehouse(mainWarehouse);
        warehouseService.createWarehouse(smallWarehouse);

        var mainWarehouseId = warehouseService.getWarehouseByName("Main").getId();
        var smallWarehouseId = warehouseService.getWarehouseByName("Small").getId();

        var productUsbCable = new ProductCreation(
                "kabel USB typu c",
                new BigDecimal("150.00"),
                "Super ultra kabel USB typu c",
                "Apple");

        var productPhone = new ProductCreation(
                "Smartfon SAMSUNG Galaxy S24",
                new BigDecimal("2400.00"),
                "Smartfon SAMSUNG Galaxy S24 5G 8/128GB 6.2\" 120Hz Czarny SM-S921",
                "SAMSUNG");

        var productKeyboard = new ProductCreation(
                "Klawiatura REDRAGON K617 Fizz RGB Biały",
                new BigDecimal("120.00"),
                "Klawiatura REDRAGON K617 Fizz RGB Biały",
                "REDDRAGON");

        productService.createProduct(productUsbCable);
        productService.createProduct(productPhone);
        productService.createProduct(productKeyboard);

        var usbCableId = productService.searchProduct(productUsbCable.getProductName()).get(0).getId();
        var phoneId = productService.searchProduct(productPhone.getProductName()).get(0).getId();
        var keyboardId = productService.searchProduct(productKeyboard.getProductName()).get(0).getId();

        warehouseService.addProductToWarehouse(mainWarehouseId, usbCableId, 1000);
        warehouseService.addProductToWarehouse(smallWarehouseId, usbCableId, 100);
        warehouseService.addProductToWarehouse(mainWarehouseId, phoneId, 100);
        warehouseService.addProductToWarehouse(smallWarehouseId, phoneId, 1);
        warehouseService.addProductToWarehouse(mainWarehouseId, keyboardId, 100);
        warehouseService.addProductToWarehouse(smallWarehouseId, keyboardId, 10);


    }

    @Override
    public void run(String... args) throws Exception {
        initialize();
    }
}
