package com.embarcados.api.features.auth.exceptions;

public class InvalidCredentialsException extends RuntimeException {

  public InvalidCredentialsException() {
    super("Email ou senha inválidos");
  }

  public InvalidCredentialsException(String message) {
    super(message);
  }
}
