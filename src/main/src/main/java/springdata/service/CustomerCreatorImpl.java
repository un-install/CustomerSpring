package springdata.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import springdata.DTO.CustomerRequest;
import springdata.entity.Customer;
import springdata.entity.Salesrep;

@Service
public class CustomerCreatorImpl implements CustomerCreator{
    private static final Logger LOG = LogManager.getLogger(CustomerCreatorImpl.class);

    @Override
    public Customer createCustomer(CustomerRequest req) {
        LOG.info("createCustomer, customerRequest={}", req);
        Customer result = new Customer();
        result.setCustNum(req.getCustNum());
        result.setCreditLimit(req.getCreditLimit());
        result.setCompany(req.getCustomerDetails().getCompany());

        return result;
    }
}
