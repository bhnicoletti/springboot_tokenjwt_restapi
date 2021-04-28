package com.bhnicoletti.back_loja.rest;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    @Getter
    private List<String> erros;

    public ApiErrors(String msg){
        this.erros = Arrays.asList(msg);
    }

    public ApiErrors(List<String> erros) {
        this.erros = erros;
    }
}
