package springdata.controllers;

import entities.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springdata.service.CustomerService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger LOG = LogManager.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public @ResponseBody Customer getOrderById(@PathVariable("id") int id) {
        LOG.info("getOrderById start, id={}", id);
        Customer result = customerService.findCustomerById(BigDecimal.valueOf(id));
        LOG.info("getOrderById end");
        return result;
    }
}
