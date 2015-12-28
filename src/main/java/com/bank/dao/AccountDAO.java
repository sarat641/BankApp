package com.bank.dao;

import com.bank.api.DAO;
import com.bank.entities.AccountEntity;
import com.bank.exceptions.AccountNotFoundException;
import java.util.Collection;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 *
 * @author SARAT
 */
@Repository
@Transactional
//@Primary
public class AccountDAO implements DAO {

    private static final String SUCCESS = "success";

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountEntity save(AccountEntity account) {

        return accountRepository.save(account);

    }

    @Override
    public AccountEntity find(Integer id) {
        AccountEntity account = accountRepository.findOne(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }
        return account;
    }

    @Override
    public Collection<AccountEntity> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Collection<AccountEntity> findAllBySessionUser(String sessionId) {
        return accountRepository.findByUserId(sessionId);
    }

    @Override
    public String delete(Integer id) {
        AccountEntity account = accountRepository.findOne(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }
        accountRepository.delete(account);
        return SUCCESS;
    }

    @Override
    public AccountEntity update(AccountEntity accountDTO) {

        AccountEntity account = accountRepository.findOne(accountDTO.getId());
        if (account == null) {
            throw new AccountNotFoundException(accountDTO.getId());
        } else {
            account.setIban(accountDTO.getIban());
            account.setBic(accountDTO.getBic());
            account.setVersion(accountDTO.getVersion());
        }
        return account;

    }

}
