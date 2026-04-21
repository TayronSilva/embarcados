package com.embarcados.api.features.auth.exceptions;

public class InvalidUserTypeException extends RuntimeException {

  public InvalidUserTypeException() {
    super("Tipo de usuário inválido. Use 'Empresa' ou 'Motorista'");
  }

  public InvalidUserTypeException(String message) {
    super(message);
  }
}
