package springdata.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import springdata.DTO.CustomerRequest;
import springdata.entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springdata.exception.UpdateException;
import springdata.service.CustomerCreator;
import springdata.service.CustomerService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger LOG = LogManager.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerCreator customerCreator;

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

    @PutMapping("/")
    @ApiOperation(authorizations = {@Authorization(value = "basicAuth")}, value = "updateCustomer")
    public void updateCustomer(@Valid @RequestBody CustomerRequest req){
        LOG.info("updateCustomer start, id={}", req.getCustNum());
        if (customerService.findCustomerById(req.getCustNum()) == null) {
            throw new UpdateException("Entity with this id do not Exist!");
        }
        customerService.updateCustomer(customerCreator.createCustomer(req));
        LOG.info("updateCustomer end");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(authorizations = {@Authorization(value = "basicAuth")}, value = "deleteCustomer")
    public void deleteCustomer(@PathVariable("id")int id){
        LOG.info("deleteCustomer start, id={}", id);
        customerService.deleteCustomer(new BigDecimal(id));
        LOG.info("deleteCustomer end");
    }

    @PostMapping("/")
    @ApiOperation(authorizations = {@Authorization(value = "basicAuth")}, value = "insertCustomer")
    public void insertCustomer (@Valid @RequestBody CustomerRequest req){
        LOG.info("insertCustomer, customerReq={}", req);
        customerService.insertCustomer(customerCreator.createCustomer(req));
        LOG.info("insertCustomer end");
    }
}
