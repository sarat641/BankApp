package com.bank.exceptions;

import com.bank.dto.FieldErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author SARAT
 */
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({org.springframework.http.converter.HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String resolveException() {
        return "Invalid JSON Format";
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<FieldErrorDTO> accountNotFound(AccountNotFoundException e) {
        Integer id = e.getId();
        FieldErrorDTO error = new FieldErrorDTO("Id", "Account " + id + " not found");
        ResponseEntity response = new ResponseEntity(error, HttpStatus.NOT_FOUND);
        return response;
    }


}
