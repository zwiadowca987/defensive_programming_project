package com.example.dpp;

import com.example.dpp.model.PurchaseStatus;
import com.example.dpp.model.api.products.PurchaseCreation;
import com.example.dpp.model.api.products.PurchaseInfo;

import com.example.dpp.model.db.customer.Customer;
import com.example.dpp.model.db.products.Product;
import com.example.dpp.model.db.products.Purchase;
import com.example.dpp.repository.CustomerRepository;
import com.example.dpp.repository.ProductRepository;
import com.example.dpp.repository.PurchaseRepository;
import com.example.dpp.services.PurchaseService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    private Customer customer;
    private Product product;
    private Purchase purchase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(1);
        customer.setCustomerFirstName("John");
        customer.setPurchases(Set.of());

        product = new Product();
        product.setId(10);
        product.setProductName("Item A");

        purchase = new Purchase();
        purchase.setId(100);
        purchase.setCustomer(customer);
        purchase.setDate(LocalDateTime.now());
        purchase.setStatus(PurchaseStatus.IN_PROGRESS);
    }

    @Test
    void getPurchases_returnsListOfPurchaseInfo() {
        when(purchaseRepository.findAll()).thenReturn(List.of(purchase));

        List<PurchaseInfo> result = purchaseService.getPurchases();

        assertEquals(1, result.size());
        verify(purchaseRepository).findAll();
    }

    @Test
    void getPurchasesByCustomerId_existingCustomer_returnsPurchases() {
        purchase.setCustomer(customer);
        customer.setPurchases(Set.of(purchase));

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        List<PurchaseInfo> result = purchaseService.getPurchasesByCustomerId(1);

        assertEquals(1, result.size());
        verify(customerRepository).findById(1);
    }

    @Test
    void getPurchase_existingId_returnsPurchaseInfo() {
        when(purchaseRepository.findById(100)).thenReturn(Optional.of(purchase));

        PurchaseInfo result = purchaseService.getPurchase(100);

        assertNotNull(result);
    }

    @Test
    void createPurchase_validData_returnsPurchaseInfo() {
        PurchaseCreation creation = new PurchaseCreation();
        creation.setClientId(1);
        creation.setDate(LocalDateTime.now());
        creation.setStatus(PurchaseStatus.PAID);

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(purchaseRepository.save(any(Purchase.class))).thenAnswer(i -> i.getArgument(0));

        PurchaseInfo result = purchaseService.createPurchase(creation);

        assertEquals(PurchaseStatus.PAID, result.getStatus());
        verify(purchaseRepository).save(any(Purchase.class));
    }

    @Test
    void deletePurchase_existingId_returnsTrue() {
        doNothing().when(purchaseRepository).deleteById(100);

        boolean result = purchaseService.deletePurchase(100);

        assertTrue(result);
        verify(purchaseRepository).deleteById(100);
    }

    @Test
    void updatePurchase_validData_returnsTrue() {
        PurchaseCreation creation = new PurchaseCreation();
        creation.setClientId(1);
        creation.setDate(LocalDateTime.now());
        creation.setStatus(PurchaseStatus.SEND);

        when(purchaseRepository.findById(100)).thenReturn(Optional.of(purchase));
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(purchaseRepository.save(any(Purchase.class))).thenAnswer(i -> i.getArgument(0));

        boolean result = purchaseService.updatePurchase(100, creation);

        assertTrue(result);
        verify(purchaseRepository).save(purchase);
    }

    @Test
    void addProduct_validData_returnsTrue() {
        when(productRepository.findById(10)).thenReturn(Optional.of(product));
        when(purchaseRepository.findById(100)).thenReturn(Optional.of(purchase));
        when(purchaseRepository.save(any(Purchase.class))).thenAnswer(i -> i.getArgument(0));

        boolean result = purchaseService.addProduct(100, 10, 5);

        assertTrue(result);
    }

    @Test
    void existsById_whenFound_returnsTrue() {
        when(purchaseRepository.existsById(100)).thenReturn(true);

        assertTrue(purchaseService.existsById(100));
    }

    // ---------- Negative tests ----------

    @Test
    void getPurchase_nonExistingId_throwsException() {
        when(purchaseRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> purchaseService.getPurchase(999));
    }

    @Test
    void getPurchasesByCustomerId_customerNotFound_throwsException() {
        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> purchaseService.getPurchasesByCustomerId(999));
    }

    @Test
    void createPurchase_customerNotFound_throwsException() {
        PurchaseCreation creation = new PurchaseCreation();
        creation.setClientId(999);

        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> purchaseService.createPurchase(creation));
    }

    @Test
    void updatePurchase_purchaseNotFound_throwsException() {
        when(purchaseRepository.findById(999)).thenReturn(Optional.empty());

        PurchaseCreation creation = new PurchaseCreation();
        creation.setClientId(1);

        assertThrows(EntityNotFoundException.class, () -> purchaseService.updatePurchase(999, creation));
    }

    @Test
    void updatePurchase_customerNotFound_throwsException() {
        when(purchaseRepository.findById(100)).thenReturn(Optional.of(purchase));
        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        PurchaseCreation creation = new PurchaseCreation();
        creation.setClientId(999);

        assertThrows(EntityNotFoundException.class, () -> purchaseService.updatePurchase(100, creation));
    }

    @Test
    void addProduct_productNotFound_throwsException() {
        when(productRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> purchaseService.addProduct(100, 999, 1));
    }

    @Test
    void addProduct_purchaseNotFound_throwsException() {
        when(productRepository.findById(10)).thenReturn(Optional.of(product));
        when(purchaseRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> purchaseService.addProduct(999, 10, 1));
    }
}
