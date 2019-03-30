package springdata.repository;

import entities.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springdata.config.DataConfig;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataConfig.class)
@TestPropertySource("classpath:springdata/repository/test.properties")
public class CustomerRepoIntegrationH2Test {
    private final Customer EXISTED_CUSTOMER = new Customer(new BigDecimal(111113),"Solomon", null, new BigDecimal(151515), null);
    private final Customer NOT_EXISTED_CUSTOMER = new Customer(new BigDecimal(21111),"Microsoft", null, new BigDecimal(50000), null);

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private DataSource dataSource;

    @Before
    public void initDB(){
        Resource initSchema = new ClassPathResource("springdata\\repository\\script\\create-db.sql");
        Resource data = new ClassPathResource("springdata\\repository\\script\\insert-data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, data);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Test
    public void findByIdExistTest(){
        Customer actual = customerRepo.findById(new BigDecimal(111112)).get();
        assertTrue(actual != null);
    }

    @Test (expected = NoSuchElementException.class)
    public void findByIdNotExistTest(){
        Customer actual = customerRepo.findById(new BigDecimal(0)).get();
    }

    @Test
    public void findAllTest(){
        List<Customer> actual = customerRepo.findAll();
        assertTrue(actual.size() >= 4);
    }

    @Test
    public void insertNotExistTest(){
        customerRepo.save(NOT_EXISTED_CUSTOMER);
        assertNotNull(customerRepo.findById(NOT_EXISTED_CUSTOMER.getCustNum()));
    }

    @Test
    public void updateExistTest(){
        Customer before = customerRepo.findById(EXISTED_CUSTOMER.getCustNum()).get();
        customerRepo.save(EXISTED_CUSTOMER);
        Customer after = customerRepo.findById(EXISTED_CUSTOMER.getCustNum()).get();
        assertNotEquals(before, after);
    }

    @Test
    public void updateNotExistTest(){
        customerRepo.save(NOT_EXISTED_CUSTOMER);
        assertNotNull(customerRepo.findById(NOT_EXISTED_CUSTOMER.getCustNum()));
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteExistTest(){
        customerRepo.delete(EXISTED_CUSTOMER);
        customerRepo.findById(EXISTED_CUSTOMER.getCustNum()).get();
    }

    @Test
    public void findByCreditLimitBetweenTest(){
        List<Customer> actual = customerRepo.findByCreditLimitBetween(new BigDecimal(100), new BigDecimal(500));
        assertTrue(actual.size() >= 1);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findByCreditLimitBetweenIllegalArgumentExceptionTest(){
        List<Customer> actual = customerRepo.findByCreditLimitBetween(null, new BigDecimal(500));
    }
}
