package com.bhnicoletti.back_loja.exception;

public class SenhaInvalidaException extends RuntimeException{
    public SenhaInvalidaException() {
        super("Senha Inválida");
    }
}
