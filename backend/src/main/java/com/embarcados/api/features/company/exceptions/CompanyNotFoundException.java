package com.embarcados.api.features.company.exceptions;

public class CompanyNotFoundException extends RuntimeException {

  public CompanyNotFoundException() {
    super("Empresa não encontrada");
  }

  public CompanyNotFoundException(String message) {
    super(message);
  }
}
