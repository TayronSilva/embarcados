package com.embarcados.api.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.embarcados.api.features.company.exceptions.CompanyNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(CompanyNotFoundException.class)
  private ResponseEntity<RestErrorMessage> companyNotFoundHandler(CompanyNotFoundException exception) {
    RestErrorMessage threadResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threadResponse);
  }

}
