package com.embarcados.api.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.embarcados.api.features.auth.exceptions.InvalidCredentialsException;
import com.embarcados.api.features.auth.exceptions.InvalidUserTypeException;
import com.embarcados.api.features.company.exceptions.CompanyNotFoundException;
import com.embarcados.api.features.driver.exceptions.DriverNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CompanyNotFoundException.class)
  private ResponseEntity<RestErrorMessage> companyNotFoundHandler(CompanyNotFoundException exception) {
    RestErrorMessage errorResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(DriverNotFoundException.class)
  private ResponseEntity<RestErrorMessage> driverNotFoundHandler(DriverNotFoundException exception) {
    RestErrorMessage errorResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  private ResponseEntity<RestErrorMessage> invalidCredentialsHandler(InvalidCredentialsException exception) {
    RestErrorMessage errorResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
  }

  @ExceptionHandler(InvalidUserTypeException.class)
  private ResponseEntity<RestErrorMessage> invalidUserTypeHandler(InvalidUserTypeException exception) {
    RestErrorMessage errorResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

}
