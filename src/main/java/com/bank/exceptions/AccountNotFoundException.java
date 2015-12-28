
package com.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author SARAT
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND,
        reason = "Account Not Found")
public class AccountNotFoundException extends RuntimeException {

    private final Integer id;

    public AccountNotFoundException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
