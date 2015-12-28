package com.bank.resources;

import com.bank.api.DAO;
import com.bank.dto.ValidationErrorDTO;
import com.bank.entities.AccountEntity;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author SARAT
 */
@RestController
@RequestMapping("/bank/rest/api")
public class AccountResource {

    private final MessageSource messageSource;
    private final DAO dao;

    @Autowired
    public AccountResource(MessageSource messageSource, DAO dao) {
        this.messageSource = messageSource;
        this.dao = dao;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public AccountEntity find(@PathVariable Integer id) {
        return dao.find(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createAccount")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountEntity save(@RequestBody @Valid AccountEntity account, HttpSession session) {
        account.setUserId(session.getId());
        return dao.save(account);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public String delete(@PathVariable Integer id) {
        return dao.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateAccount")
    public AccountEntity update(@RequestBody @Valid AccountEntity account) {
        return dao.update(account);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    private ValidationErrorDTO processFieldErrors(List<FieldError> fieldErrors) {
        ValidationErrorDTO dto = new ValidationErrorDTO();

        fieldErrors.stream().forEach((fieldError) -> {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            dto.addFieldError(fieldError.getField(), localizedErrorMessage);
        });

        return dto;
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);
        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }

        return localizedErrorMessage;
    }

}
