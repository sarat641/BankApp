package com.bank.dao;

import com.bank.api.DAO;
import com.bank.RepositoryConfiguration;
import com.bank.entities.AccountEntity;
import com.bank.exceptions.AccountNotFoundException;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.util.Collection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * @author SARAT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup(value = {"/data/Account.xml", "/data/ID_GENERATOR.xml"})
@WebAppConfiguration
public class DAOTest {

    @Autowired
    DAO dao;

    @Test
    public void daoShouldNotBeNull() {
        assertNotNull(dao);
    }

    @Test
    public void findAll() {
        Collection<AccountEntity> listOfAccount = dao.findAll();
        assertEquals(1, listOfAccount.size());
    }

    @Test
    public void save() {
        String iban = "GB89MIDL40051537063729x1";
        String bic = "MIDLGB22";

        AccountEntity account = new AccountEntity();
        account.setIban(iban);
        account.setBic(bic);

        dao.save(account);

        assertNotNull(account.getId());
        assertNotNull(account.getVersion());
    }

    @Test
    public void find() {
        AccountEntity account = dao.find(1);
        assertNotNull(account);
        assertEquals(account.getId(), Integer.valueOf(1));
    }

    @Test(expected = AccountNotFoundException.class)
    public void findInvalidId() {
        dao.find(2);
    }

    @Test
    public void delete() {
        dao.delete(1);
        Collection<AccountEntity> listOfAccounts = dao.findAll();
        assertEquals(listOfAccounts.size(), (long) 0);
    }

    @Test(expected = AccountNotFoundException.class)
    public void deleteInvalidId() {
        dao.delete(2);
    }

    @Test
    public void update() {
        String iban = "GB89MIDL40051537063729";
        String bic = "MIDLGB22";

        AccountEntity beforeSave = new AccountEntity();
        beforeSave.setIban(iban);
        beforeSave.setBic(bic);
        beforeSave.setId(1);
        beforeSave.setVersion(1);
        dao.update(beforeSave);

        assertEquals(iban, beforeSave.getIban());
        assertEquals(bic, beforeSave.getBic());
    }

}
