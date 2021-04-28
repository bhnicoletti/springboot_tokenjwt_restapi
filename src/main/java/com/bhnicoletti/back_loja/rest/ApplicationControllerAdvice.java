package com.bhnicoletti.back_loja.rest;


import com.bhnicoletti.back_loja.exception.ObjetoNaoEncontradoException;
import com.bhnicoletti.back_loja.exception.ProdutoSemEstoqueException;
import com.bhnicoletti.back_loja.exception.SenhaInvalidaException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleServiceException( ServiceException ex){
        String msg = ex.getMessage();
        return new ApiErrors(msg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMothodNotValidException(MethodArgumentNotValidException ex){
        List<String> erros = ex.getBindingResult().getAllErrors().stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(erros);
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleSenhaInvalidaException(SenhaInvalidaException ex){
        String msg = ex.getMessage();
        return new ApiErrors(msg);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrors handleUsernameNotFound(UsernameNotFoundException ex){
        String msg = ex.getMessage();
        return new ApiErrors(msg);
    }

    @ExceptionHandler(ObjetoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handleObjetoNaoEncontrado(ObjetoNaoEncontradoException ex){
        String msg = ex.getMessage();
        return new ApiErrors(msg);
    }

    @ExceptionHandler(ProdutoSemEstoqueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrors handleProdutoSemEstoque(ProdutoSemEstoqueException ex){
        String msg = ex.getMessage();
        return new ApiErrors(msg);
    }
}
