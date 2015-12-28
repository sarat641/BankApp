package com.bank.session;

import com.bank.beans.Sequencer;
import com.bank.api.DAO;
import com.bank.entities.AccountEntity;
import com.bank.exceptions.AccountNotFoundException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 *
 * @author SARAT
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Primary
public class SessionDAO implements DAO, Serializable {

    @Autowired
    private Sequencer sequencer;

    private static final String SUCCESS = "success";

    private final Map<Integer, AccountEntity> accountMap = new ConcurrentHashMap<>();

    @Override
    public String delete(Integer id) {
        AccountEntity account = accountMap.get(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }
        accountMap.remove(id);
        return SUCCESS;
    }

    @Override
    public AccountEntity find(Integer id) {
        AccountEntity account = accountMap.get(id);
        if (account == null) {
            throw new AccountNotFoundException(id);
        }
        return account;
    }

    @Override
    public Collection<AccountEntity> findAll() {
        return accountMap.values();
    }

    @Override
    public Collection<AccountEntity> findAllBySessionUser(String sessionId) {
        return accountMap.values();
    }

    @Override
    public AccountEntity save(AccountEntity accountDTO) {
        int id = sequencer.next();
        accountDTO.setId(id);
        accountDTO.setVersion(0);
        accountMap.put(id, accountDTO);
        return accountDTO;
    }

    @Override
    public AccountEntity update(AccountEntity accountDTO) {

        AccountEntity account = accountMap.get(accountDTO.getId());
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
