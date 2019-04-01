package springdata.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import springdata.DTO.CustomerRequest;
import springdata.entity.Customer;
import springdata.utils.DtoModelsUtils;

import static org.junit.Assert.assertEquals;

public class CustomerCreatorTest {

    private CustomerCreator customerCreator = new CustomerCreatorImpl();

    @Test
    public void testCreateCustomer(){
        CustomerRequest customerRequest = DtoModelsUtils.customerRequest();
        Customer actual = customerCreator.createCustomer(customerRequest);
        Customer expected = DtoModelsUtils.customer();
        assertEquals(expected.getCustNum(), actual.getCustNum());
        assertEquals(expected.getCompany(), actual.getCompany());
        assertEquals(expected.getCreditLimit(), actual.getCreditLimit());
    }
}
