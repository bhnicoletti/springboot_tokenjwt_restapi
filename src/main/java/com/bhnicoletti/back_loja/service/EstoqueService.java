package com.bhnicoletti.back_loja.service;

import com.bhnicoletti.back_loja.model.Produto;

public interface EstoqueService {
    Produto baixaEstoque(Produto produto, Integer quant);
    Produto creditaEstoque(Produto produto, Integer quant);
}
