package com.bank.api;

import com.bank.entities.AccountEntity;
import java.util.Collection;

/**
 *
 * @author SARAT
 */
public interface DAO {

    String delete(Integer id);

    AccountEntity find(Integer id);

    Collection<AccountEntity> findAll();

    Collection<AccountEntity> findAllBySessionUser(String sessionId);

    AccountEntity save(AccountEntity account);

    AccountEntity update(AccountEntity account);

}
