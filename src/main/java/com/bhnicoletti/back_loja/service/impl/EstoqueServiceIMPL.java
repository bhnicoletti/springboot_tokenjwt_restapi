package com.bhnicoletti.back_loja.service.impl;

import com.bhnicoletti.back_loja.model.Produto;
import com.bhnicoletti.back_loja.repository.ProdutoRepository;
import com.bhnicoletti.back_loja.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueServiceIMPL implements EstoqueService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    public Produto baixaEstoque(Produto produto, Integer quant) {
        produto.setEstoqueProduto(produto.getEstoqueProduto()-quant);
        return produtoRepository.save(produto);

    }

    @Override
    public Produto creditaEstoque(Produto produto, Integer quant) {
        produto.setEstoqueProduto(produto.getEstoqueProduto()+quant);
        return produtoRepository.save(produto);
    }
}
