package springdata.service;

import entities.Customer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import springdata.config.DataConfig;
import springdata.exceptions.DeleteException;
import springdata.exceptions.FindByIdException;
import springdata.repository.CustomerRepo;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOG = LogManager.getLogger(DataConfig.class);
    @Autowired
    private CustomerRepo custRepository;

    @Override
    public Set<Customer> getAllCustomers() {
        LOG.debug("get all Customers");
        HashSet<Customer> customers = new HashSet<>(custRepository.findAll());
        LOG.debug("get all customers complete");
        return customers;
    }

    @Override
    public Set<Customer> findByCreditLimitBetween(int minLimit, int maxLimit) {
        LOG.debug("finding customers by credit limit between");
        HashSet<Customer> customers = new HashSet<>(custRepository.findByCreditLimitBetween(minLimit, maxLimit));
        LOG.debug("finding customers by credit limit between complete");
        return customers;
    }

    @Override
    public Customer findCustomerById(BigDecimal id) {
        LOG.debug("findCustomerById");
        Customer cust = null;
        try {
            cust = custRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        } catch (ChangeSetPersister.NotFoundException e) {
            LOG.warn("cannot findCustomer with id{"+ id +"}, because dont present");
            throw new FindByIdException("Cannot find Customer by Id=" + id + ", because it dont present");
        }
        LOG.debug("findCustomerById complete");
        return cust;
    }

    @Override
    public void insertCustomer(Customer customer) {
        LOG.debug("insert Customer");
        custRepository.save(customer);
        LOG.debug("insert Customer completed");
    }

    @Override
    public void updateCustomer(Customer customer) {
        LOG.debug("update Customer");
        custRepository.save(customer);
        LOG.debug("update Customer completed");
    }

    @Override
    public void deleteCustomer(BigDecimal id) {
        LOG.debug("deleteCustomer, id={" + id + "}");
        try {
            custRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            LOG.warn("cannot deleteCustomer with id{"+ id +"}, because dont present");
            throw new DeleteException("Cannot deleteCustomer by Id=" + id + ", because it dont present");
        }
        LOG.debug("deleteCustomer completed");
    }
}
