package com.bank.beans;

import com.bank.entities.AccountEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 *
 * @author SARAT
 */
@Component
public class DefaultValues {

    private final Map<Integer, AccountEntity> accountMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        loadDefaultAccounts();
    }

    private void loadDefaultAccounts() {
        AccountEntity account1 = new AccountEntity(1, "IE64BOFI90583812345678", "BOFIIE2D");
        account1.setVersion(0);
        accountMap.put(1, account1);

        AccountEntity account2 = new AccountEntity(2, "GB89MIDL40051537063729", "MIDLGB22");
        account2.setVersion(0);
        accountMap.put(2, account2);

        AccountEntity account3 = new AccountEntity(3, "DE91501108006231605988", "CHASDEFXXXX");
        account3.setVersion(0);
        accountMap.put(3, account3);

        AccountEntity account4 = new AccountEntity(4, "CH2304835094601863000", "CRESCHZZ80A");
        account4.setVersion(0);
        accountMap.put(4, account4);

        AccountEntity account5 = new AccountEntity(5, "DK0630003996056694", "DABADKKKXXX");
        account5.setVersion(0);
        accountMap.put(5, account5);
    }

    public Collection<AccountEntity> getDefaultCollection() {
        return Collections.unmodifiableCollection(accountMap.values());
    }
}
