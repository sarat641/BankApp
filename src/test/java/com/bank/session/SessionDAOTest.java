package com.bank.session;

import com.bank.TestContext;
import com.bank.api.DAO;
import com.bank.entities.AccountEntity;
import com.bank.exceptions.AccountNotFoundException;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author SARAT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TestContext.class})
@WebAppConfiguration
public class SessionDAOTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DAO sessionDAO;

    @Test
    public void shouldAutowireBeans() {
        assertNotNull(sessionDAO);
        assertNotNull(wac);
    }

    @Test
    public void sessionScope() throws Exception {
        org.assertj.core.api.Assertions.assertThat(sessionDAO).isSameAs(wac.getBean("sessionDAO", SessionDAO.class));
    }

    @Test
    public void save() {
        String iban = "GB89MIDL40051537063729x1";
        String bic = "MIDLGB22";

        AccountEntity account = new AccountEntity();
        account.setIban(iban);
        account.setBic(bic);

        sessionDAO.save(account);

        assertNotNull(account.getId());
        assertNotNull(account.getVersion());

        DAO dao = (DAO) wac.getBean("sessionDAO");
        AccountEntity acountInSession = dao.find(account.getId());
        assertNotNull(acountInSession);
    }

    @Test
    public void findAll() {
        Collection<AccountEntity> listOfAccount = sessionDAO.findAll();
        DAO dao = (DAO) wac.getBean("sessionDAO");
        assertEquals(listOfAccount.size(), dao.findAll().size());
    }

    @Test
    public void find() {

        String iban = "GB89MIDL40051537063729x1";
        String bic = "MIDLGB22";

        AccountEntity account = new AccountEntity();
        account.setIban(iban);
        account.setBic(bic);

        sessionDAO.save(account);

        DAO dao = (DAO) wac.getBean("sessionDAO");
        AccountEntity account1 = dao.find(6);
        assertNotNull(account1);
        assertEquals(account.getId(), Integer.valueOf(6));
    }

    @Test(expected = AccountNotFoundException.class)
    public void findInvalidId() {
        DAO dao = (DAO) wac.getBean("sessionDAO");
        dao.find(200);
    }

    @Test(expected = AccountNotFoundException.class)
    public void delete() {
        DAO dao = (DAO) wac.getBean("sessionDAO");
        dao.delete(5);
        AccountEntity account = dao.find(5);
    }

    @Test(expected = AccountNotFoundException.class)
    public void deleteInvalidId() {
        DAO dao = (DAO) wac.getBean("sessionDAO");
        dao.delete(150);
    }
}
