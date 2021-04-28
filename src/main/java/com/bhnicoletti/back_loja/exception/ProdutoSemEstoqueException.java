package com.bhnicoletti.back_loja.exception;

public class ProdutoSemEstoqueException extends RuntimeException {

    public ProdutoSemEstoqueException(String message) {
        super(message);
    }
}
