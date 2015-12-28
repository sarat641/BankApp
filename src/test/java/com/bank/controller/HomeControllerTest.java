package com.bank.controller;

import com.bank.TestContext;
import com.bank.api.DAO;
import com.bank.beans.DefaultValues;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author SARAT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MockServletContext.class, TestContext.class})
@WebAppConfiguration
public class HomeControllerTest {

    private final String URL = "/";
    private MockMvc mvc;
    @Mock
    private DAO service;
    @Mock
    private DefaultValues defaultValues;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(new HomeController(service, defaultValues)).build();

    }

    @Test
    public void notNull() {
        assertNotNull(service);
        assertNotNull(defaultValues);
    }

    @Test
    public void index() throws Exception {


        mvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
        
    }

    
}
