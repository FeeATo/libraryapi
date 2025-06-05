package io.github.feeato.libraryapi.exceptions;

public class RegistroNaoEncontradoException extends RuntimeException {
    public RegistroNaoEncontradoException(String message, String entidade, String id) {
        super(message + ". Entidade " + entidade + "(ID " + id+")");
    }
}
