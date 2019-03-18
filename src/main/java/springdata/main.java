package springdata;

import entities.Customer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springdata.repository.CustomerRepo;
import springdata.service.CustomerServiceImpl;

import javax.xml.ws.Service;
import java.math.BigDecimal;
import java.util.List;

public class main {
    public static void main(String[] args) {
        Customer insertCust = new Customer(new BigDecimal(11121),"Torn", null, new BigDecimal(123321), null);
        ConfigurableApplicationContext context = new AnnotationConfigApplicationContext("springdata.config");
        CustomerServiceImpl customerService = context.getBean(CustomerServiceImpl.class);

        customerService.insertCustomer(insertCust);
        Customer cust = customerService.findCustomerById(insertCust.getCustNum());
        System.out.println(cust);

        insertCust.setCreditLimit(new BigDecimal(111111));
        insertCust.setCompany("Egegey");
        customerService.updateCustomer(insertCust);
        cust = customerService.findCustomerById(insertCust.getCustNum());
        System.out.println(cust);

        customerService.getAllCustomers().forEach(System.out::println);

        customerService.findByCreditLimitBetween(200, 3000).forEach(System.out::println);

        customerService.deleteCustomer(insertCust.getCustNum());

        context.close();
    }
}
