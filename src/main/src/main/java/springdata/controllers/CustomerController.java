package springdata.controllers;

import entities.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springdata.exceptions.DeleteException;
import springdata.exceptions.UpdateException;
import springdata.service.CustomerService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger LOG = LogManager.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public @ResponseBody Set<Customer> getAllCustomers(){
        LOG.info("getAllCustomers start");
        Set<Customer> result = customerService.getAllCustomers();
        LOG.info("getAllCustomers end");
        return result;
    }

    @GetMapping("/{id}")
    public @ResponseBody Customer getCustomerById(@PathVariable("id") int id) {
        LOG.info("getCustomerById start, id={}", id);
        Customer result = customerService.findCustomerById(new BigDecimal(id));
        LOG.info("getCustomerById end");
        return result;
    }

    @GetMapping("/{min}/{max}")
    public @ResponseBody Set<Customer> findByCreditLimitBetween(@PathVariable("min") int min, @PathVariable("max") int max){
        LOG.info("findByCreditLimitBetween start, min={} max={}", min, max);
        Set<Customer> result = customerService.findByCreditLimitBetween(new BigDecimal(min), new BigDecimal(max));
        LOG.info("findByCreditLimitBetween end");
        return result;
    }

    @PostMapping("/")
    public void updateCustomer( @RequestBody Customer customer){
        LOG.info("updateCustomer start, id={}", customer.getCustNum());
        if (customerService.findCustomerById(customer.getCustNum()) == null) {
            throw new UpdateException("Entity with this id do not Exist!");
        }
        customerService.updateCustomer(customer);
        LOG.info("updateCustomer end");
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable("id")int id){
        LOG.info("deleteCustomer start, id={}", id);
        customerService.deleteCustomer(new BigDecimal(id));
        LOG.info("deleteCustomer end");
    }
}
