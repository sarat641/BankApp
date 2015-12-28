package com.bank.resources;

import com.bank.TestContext;
import com.bank.api.DAO;
import com.bank.entities.AccountEntity;
import com.bank.util.TestUtil;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * @author SARAT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {MockServletContext.class, TestContext.class})
@WebAppConfiguration
public class AccountResourceTest {

    private final String URL = "/bank/rest/api";
    private MockMvc mvc;
    @Mock
    DAO service;

    @Autowired
    private MessageSource messageSource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(new AccountResource(messageSource, service)).build();

    }

    @Test
    public void find() throws Exception {
        String iban = "GB89MIDL40051537063729";
        String bic = "MIDLGB22";

        AccountEntity account = new AccountEntity(1, iban, bic);
        account.setVersion(0);

        when(service.find(1)).thenReturn(account);

        mvc.perform(get(URL.concat("/1")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.iban", is(iban)))
                .andExpect(jsonPath("$.bic", is(bic)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.version", is(0)));
    }

    @Test
    public void save() throws Exception {
        String iban = "GB89MIDL40051537063729";
        String bic = "MIDLGB22";

        AccountEntity beforeSave = new AccountEntity();
        beforeSave.setIban(iban);
        beforeSave.setBic(bic);

        AccountEntity account = new AccountEntity(1, iban, bic);
        account.setVersion(0);

        when(service.save(beforeSave)).thenReturn(account);

        mvc.perform(post(URL.concat("/createAccount")).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(beforeSave))).andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.iban", is(iban)))
                .andExpect(jsonPath("$.bic", is(bic)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.version", is(0)));
        verify(service, times(1)).save(beforeSave);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void deleteAccount() throws Exception {
        when(service.delete(1)).thenReturn("success");
        mvc.perform(delete(URL.concat("/1")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().string(equalTo("success")));
        verify(service, times(1)).delete(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void update() throws Exception {

        String iban = "GB89MIDL40051537063729";
        String bic = "MIDLGB22";

        AccountEntity beforeSave = new AccountEntity();
        beforeSave.setIban(iban);
        beforeSave.setBic(bic);
        beforeSave.setId(0);
        beforeSave.setVersion(0);

        AccountEntity account = new AccountEntity(1, iban, bic);
        account.setVersion(1);

        when(service.update(beforeSave)).thenReturn(account);

        mvc.perform(post(URL.concat("/updateAccount")).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(beforeSave))).andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.iban", is(iban)))
                .andExpect(jsonPath("$.bic", is(bic)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.version", is(1)));
        verify(service, times(1)).update(beforeSave);
        verifyNoMoreInteractions(service);

    }

    @Test
    public void saveInvalidData() throws Exception {
        AccountEntity beforeSave = new AccountEntity();

        mvc.perform(post(URL.concat("/createAccount")).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(beforeSave)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
                .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder(
                                        "iban", "bic")))
                .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
                                        "IBAN can not be blank",
                                        "Business Identifier Code can not be blank"
                                )));
        verifyZeroInteractions(service);
    }

    @Test
    public void updateInvalidData() throws Exception {
        AccountEntity beforeSave = new AccountEntity();

        mvc.perform(post(URL.concat("/updateAccount")).contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(beforeSave)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.fieldErrors", hasSize(2)))
                .andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder(
                                        "iban", "bic")))
                .andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
                                        "IBAN can not be blank",
                                        "Business Identifier Code can not be blank"
                                )));
        verifyZeroInteractions(service);
    }
}
