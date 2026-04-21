package com.embarcados.api.features.auth.exceptions;

public class InvalidUserRoleException extends RuntimeException {

    public InvalidUserRoleException() {
        super("Perfil de acesso inválido. Use 'Empresa' ou 'Motorista'");
    }

    public InvalidUserRoleException(String message) {
        super(message);
    }
}
