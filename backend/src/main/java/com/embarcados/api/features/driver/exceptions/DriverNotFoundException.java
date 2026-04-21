package com.embarcados.api.features.driver.exceptions;

public class DriverNotFoundException extends RuntimeException {

  public DriverNotFoundException() {
    super("Motorista não encontrado");
  }

  public DriverNotFoundException(String message) {
    super(message);
  }
}
