package springdata.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import springdata.DTO.CustomerRequest;
import springdata.config.MainMvcConfig;

import springdata.utils.DtoModelsUtils;

import java.math.BigDecimal;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainMvcConfig.class})
@WebAppConfiguration
@TestPropertySource("classpath:springdata/repository/test.properties")
@PropertySource("classpath:springdata/config/database.properties")
public class CustomerControllerSecurityTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DataSource dataSource;

    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();
    private Gson gson = new Gson();

    @Before
    public void initDB(){
        Resource initSchema = new ClassPathResource("springdata\\repository\\script\\create-db.sql");
        Resource data = new ClassPathResource("springdata\\repository\\script\\insert-data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, data);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testInsertCustomerAuth() throws Exception {
        String json = gson.toJson(DtoModelsUtils.customerRequest());
        MvcResult mvcResult = mockMvc.perform(post("/customer/").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCustomerExistAuth() throws Exception {
        CustomerRequest req = DtoModelsUtils.customerRequest();
        req.setCustNum(new BigDecimal(111112));
        req.setCustRep(new BigDecimal(101));
        req.setCreditLimit(new BigDecimal(900));
        MvcResult mvcResult = mockMvc.perform(put("/customer/").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(req))).andDo(print()).andReturn();

        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCustomerExistAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/customer/{id}", 111112)).andReturn();
        assertEquals(Response.Status.OK.getStatusCode(), mvcResult.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCustomerAuthNotExist() throws Exception {
        CustomerRequest req = DtoModelsUtils.customerRequest();
        req.setCustNum(new BigDecimal(11));
        MvcResult mvcResult = mockMvc.perform(put("/customer/").contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(req))).andReturn();
        assertEquals(422, mvcResult.getResponse().getStatus());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCustomerAuthNotExist() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/customer/{id}", "11")).andReturn();
        assertEquals(422, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testInsertCustomerNoAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/customer/").contentType(MediaType.APPLICATION_JSON).content("")).andReturn();
        assertEquals(401, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testUpdateCustomerNoAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(put("/customer/").contentType(MediaType.APPLICATION_JSON).content("")).andReturn();
        assertEquals(401, mvcResult.getResponse().getStatus());
    }

    @Test
    public void testDeleteCustomerNoAuth() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/customer/{id}", "11")).andReturn();
        assertEquals(401, mvcResult.getResponse().getStatus());
    }
}
