package springdata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import springdata.DTO.CustomerRequest;
import springdata.entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.ws.rs.core.Response.Status;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import springdata.config.MainMvcConfig;
import springdata.utils.DtoModelsUtils;

import javax.sql.DataSource;
import java.math.BigDecimal;


@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainMvcConfig.class})
@WebAppConfiguration
@TestPropertySource("classpath:springdata/repository/test.properties")
@PropertySource("classpath:springdata/config/database.properties")
public class CustomerControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void initDB(){
        Resource initSchema = new ClassPathResource("springdata\\repository\\script\\create-db.sql");
        Resource data = new ClassPathResource("springdata\\repository\\script\\insert-data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, data);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetCustomerById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/customer/{id}", "111112")).andReturn();
        Customer customer = mapper.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        assertNotNull(customer);
    }

    @Test
    public void testGetCustomerByIdNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/customer/{id}", "1")).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().length() == 0);
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/customer/")).andReturn();
        int length = new GsonJsonParser().parseList(mvcResult.getResponse().getContentAsString()).size();
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        assertTrue(length == 4);
    }

    @Test
    public void testFindByCreditLimitBetween() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/customer/100/400")).andReturn();
        int length = new GsonJsonParser().parseList(mvcResult.getResponse().getContentAsString()).size();
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
        assertTrue(length == 1);
    }

    @Test
    public void testFindByCreditLimitNullResult() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/customer/1/5")).andReturn();
        assertTrue(new GsonJsonParser().parseList(mvcResult.getResponse().getContentAsString()).size() == 0);
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        CustomerRequest req = DtoModelsUtils.customerRequest();
        req.setCustNum(new BigDecimal(111112));
        req.setCustRep(new BigDecimal(101));
        req.setCreditLimit(new BigDecimal(900));
        MvcResult mvcResult = mockMvc.perform(put("/customer/").contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(req))).andDo(print()).andReturn();
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testUpdateCustomerNotExist() throws Exception {
        CustomerRequest request = DtoModelsUtils.customerRequest();
        MvcResult mvcResult = mockMvc.perform(put("/customer/").contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(request))).andDo(print()).andReturn();
        assertEquals(422, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/customer/{id}", "111112")).andReturn();
        assertEquals(Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDeleteCustomerNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/customer/{id}", "1")).andReturn();
        assertEquals(422, mvcResult.getResponse().getStatus());
    }
}
