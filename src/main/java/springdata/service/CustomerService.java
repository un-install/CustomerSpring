package springdata.service;

import entities.Customer;

import java.math.BigDecimal;
import java.util.Set;

public interface CustomerService {
    Set<Customer> getAllCustomers();

    Set<Customer> findByCreditLimitBetween(int minLimit, int maxLimit);

    Customer findCustomerById(BigDecimal id);

    void insertCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(BigDecimal id);
}
