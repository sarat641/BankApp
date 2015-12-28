package com.bank.dao;

import com.bank.entities.AccountEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author SARAT
 */
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    public List<AccountEntity> findByUserId(String userId);
}
