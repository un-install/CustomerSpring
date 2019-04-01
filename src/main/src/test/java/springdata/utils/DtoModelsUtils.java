package springdata.utils;

import springdata.DTO.CustomerDetails;
import springdata.DTO.CustomerRequest;
import springdata.entity.Customer;
import springdata.entity.Salesrep;

import java.math.BigDecimal;

public class DtoModelsUtils {
    private static final BigDecimal CUSTNUM = new BigDecimal(88888);
    private static final String  COMPANY = "BDSMM";
    private static final BigDecimal SALESREP = new BigDecimal(104);
    private static final BigDecimal CREDITLIMIT = new BigDecimal(212);

    public static Customer customer() {
        Customer expected = new Customer();
        expected.setCustNum(CUSTNUM);
        expected.setCustRep(new Salesrep(SALESREP));
        expected.setCreditLimit(CREDITLIMIT);
        expected.setCompany(COMPANY);
        return expected;
    }

    public static CustomerRequest customerRequest() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setCustNum(CUSTNUM);
        customerRequest.setCustRep(SALESREP);
        customerRequest.setCreditLimit(CREDITLIMIT);
        CustomerDetails detalis = new CustomerDetails();
        detalis.setCompany(COMPANY);
        customerRequest.setCustomerDetails(detalis);
        return customerRequest;
    }
}

