package springdata.service;

import entities.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import springdata.repository.CustomerRepo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @Spy
    @InjectMocks
    private CustomerService custService = new CustomerServiceImpl();
    @Mock
    private CustomerRepo custRepo;

    private Customer cust1 = new Customer();
    private Customer cust2 = new Customer();

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(new Customer[]{cust1, cust2});
        doReturn(customers).when(custRepo).findAll();
        Set<Customer> result = custService.getAllCustomers();
        assertTrue(result.size() == customers.size() && result.containsAll(customers));
    }

    @Test
    public void testFindCustomerById() {
        doReturn(Optional.of(cust1)).when(custRepo).findById(any());
        Customer result = custService.findCustomerById(new BigDecimal(0));
        assertEquals(cust1, result);
    }

    @Test
    public void testFindByCreditLimitBetween() {
        List<Customer> customers = Arrays.asList(new Customer[] {cust1, cust2});
        doReturn(customers).when(custRepo).findByCreditLimitBetween(any(), any());
        Set<Customer> result = custService.findByCreditLimitBetween(new BigDecimal(0), new BigDecimal(0));
        assertTrue(result.size() == customers.size() && result.containsAll(customers));
    }

    @Test
    public void testInsertCustomer() {
        doReturn(cust1).when(custRepo).save(any());
        custService.insertCustomer(cust1);
        verify(custRepo, times(1)).save(any());
    }

    @Test
    public void testUpdateCustomer() {
        doReturn(cust1).when(custRepo).save(any());
        custService.updateCustomer(cust1);
        verify(custRepo, times(1)).save(any());
    }

    @Test
    public void testDeleteCustomer() {
        doNothing().when(custRepo).deleteById(any());
        custService.deleteCustomer(cust1.getCustNum());
        verify(custRepo, times(1)).deleteById(any());
    }
}
