package springdata.service;

import springdata.entity.Customer;

import java.math.BigDecimal;
import java.util.Set;

public interface CustomerService {
    Set<Customer> getAllCustomers();

    Set<Customer> findByCreditLimitBetween(BigDecimal minLimit, BigDecimal maxLimit);

    Customer findCustomerById(BigDecimal id);

    void insertCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(BigDecimal id);
}
