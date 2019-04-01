package springdata.service;

import springdata.DTO.CustomerRequest;
import springdata.entity.Customer;

public interface CustomerCreator {
    Customer createCustomer(CustomerRequest req);
}
