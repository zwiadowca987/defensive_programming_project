package com.example.dpp;


import com.example.dpp.model.api.AddressInfo;
import com.example.dpp.model.api.customer.CustomerInfo;
import com.example.dpp.model.api.customer.NewCustomer;
import com.example.dpp.model.db.Address;
import com.example.dpp.model.db.customer.Customer;
import com.example.dpp.repository.CustomerRepository;
import com.example.dpp.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer mockCustomer;
    private Address mockAddress;

    @BeforeEach
    void setup() {
        mockAddress = new Address();
        mockAddress.setCity("City");
        mockAddress.setCountry("Country");
        mockAddress.setPostalCode("00-000");
        mockAddress.setStreet("Main Street");
        mockAddress.setNumber("1");

        mockCustomer = new Customer();
        mockCustomer.setId(1);
        mockCustomer.setCustomerFirstName("John");
        mockCustomer.setCustomerLastName("Doe");
        mockCustomer.setCustomerEmail("john.doe@example.com");
        mockCustomer.setCustomerPhone("123456789");
        mockCustomer.setCustomerAddress(mockAddress);
    }

    @Test
    void getAllCustomers_returnsListOfCustomerInfo() {
        when(customerRepository.findAll()).thenReturn(List.of(mockCustomer));

        List<CustomerInfo> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getCustomerFirstName());
    }

    @Test
    void getCustomerById_existingId_returnsCustomerInfo() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(mockCustomer));

        CustomerInfo result = customerService.getCustomerById(1);

        assertEquals("John", result.getCustomerFirstName());
    }

    @Test
    void getCustomerById_nonExistingId_throwsException() {
        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> customerService.getCustomerById(999));
    }

    @Test
    void createCustomer_validInput_returnsCustomerInfo() {
        NewCustomer newCustomer = new NewCustomer();
        newCustomer.setCustomerFirstName("Anna");
        newCustomer.setCustomerLastName("Nowak");
        newCustomer.setCustomerEmail("anna@example.com");
        newCustomer.setCustomerPhone("987654321");

        AddressInfo address = new AddressInfo();
        address.setCity("Warsaw");
        address.setCountry("Poland");
        address.setPostalCode("00-001");
        address.setStreet("Testowa");
        address.setNumber("10");

        newCustomer.setCustomerAddress(address);

        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CustomerInfo result = customerService.createCustomer(newCustomer);

        assertEquals("Anna", result.getCustomerFirstName());
        assertEquals("Poland", result.getCustomerAddress().getCountry());
    }

    @Test
    void updateCustomer_existingId_returnsUpdatedCustomerInfo() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(mockCustomer));
        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        NewCustomer edit = new NewCustomer();
        edit.setCustomerFirstName("Jane");
        edit.setCustomerLastName("Smith");
        edit.setCustomerEmail("jane@example.com");
        edit.setCustomerPhone("111222333");

        AddressInfo editAddr = new AddressInfo();
        editAddr.setCity("Krakow");
        editAddr.setCountry("Poland");
        editAddr.setPostalCode("30-001");
        editAddr.setStreet("New Street");
        editAddr.setNumber("2");

        edit.setCustomerAddress(editAddr);

        CustomerInfo result = customerService.updateCustomer(1, edit);

        assertEquals("Jane", result.getCustomerFirstName());
        assertEquals("Krakow", result.getCustomerAddress().getCity());
    }

    @Test
    void updateCustomer_nonExistingId_throwsException() {
        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        NewCustomer edit = new NewCustomer();
        edit.setCustomerFirstName("Test");

        assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(999, edit));
    }

    @Test
    void deleteCustomer_callsRepositoryDeleteById() {
        doNothing().when(customerRepository).deleteById(1);

        customerService.deleteCustomer(1);

        verify(customerRepository, times(1)).deleteById(1);
    }

    @Test
    void getCustomerById_whenCustomerNotFound_throwsException() {
        // given
        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        // when + then
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> customerService.getCustomerById(999));
        assertEquals("Customer not found", ex.getMessage());
    }

    @Test
    void updateCustomer_whenCustomerNotFound_throwsException() {
        // given
        NewCustomer input = new NewCustomer();
        input.setCustomerFirstName("Ghost");

        when(customerRepository.findById(404)).thenReturn(Optional.empty());

        // when + then
        assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(404, input));
    }

    @Test
    void createCustomer_whenAddressIsNull_throwsException() {
        NewCustomer input = new NewCustomer();
        input.setCustomerFirstName("Test");
        input.setCustomerLastName("Test");
        input.setCustomerEmail("test@example.com");
        input.setCustomerPhone("123456789");
        input.setCustomerAddress(null); // błędny przypadek

        assertThrows(NullPointerException.class, () -> customerService.createCustomer(input));
    }

    @Test
    void deleteCustomer_whenIdDoesNotExist_shouldNotThrow() {

        assertDoesNotThrow(() -> customerService.deleteCustomer(999));
    }

}

