package com.bhnicoletti.back_loja.rest.controller;

import com.bhnicoletti.back_loja.exception.ObjetoNaoEncontradoException;
import com.bhnicoletti.back_loja.model.Produto;
import com.bhnicoletti.back_loja.repository.ProdutoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api/produtos/")
@Api("Api Produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @ApiOperation("Salvar novo produto")
    @ApiResponses({
            @ApiResponse(message = "Produto Salvo", code = 201),
            @ApiResponse(message = "Erro de validação", code = 400)
    })
    @PostMapping
    @ResponseStatus(CREATED)
    public Produto salvar(@RequestBody @Valid Produto produto){
        return produtoRepository.save(produto);
    }

    @ApiOperation("Atualizar produto")
    @ApiResponses({
            @ApiResponse(message = "Produto Salvo", code = 201),
            @ApiResponse(message = "Erro de validação", code = 400)
    })
    @PutMapping("{id}")
    @ResponseStatus(OK)
    public void atualizar(@RequestBody @Valid Produto produto, @RequestParam Integer id){
        produtoRepository.findById(id).map( prod -> {
            produto.setIdProduto(prod.getIdProduto());
            produtoRepository.save(produto);
            return produto;
        }).orElseThrow( () -> new ObjetoNaoEncontradoException("Produto não encontrado"));
        produtoRepository.save(produto);
    }

    @ApiOperation("Carregar produto por ID")
    @ApiResponses({
            @ApiResponse(message = "Dados Produto", code = 200),
            @ApiResponse(message = "Produto não encontrado", code = 404)
    })
    @GetMapping("{id}")
    @ResponseStatus(OK)
    public Produto carregar(@RequestParam Integer id){
        return produtoRepository.findById(id).orElseThrow( () -> new ObjetoNaoEncontradoException("Produto não encontrado"));
    }

    @ApiOperation("Deletar produto por ID")
    @ApiResponses({
            @ApiResponse(message = "Produto Deletado", code = 204),
            @ApiResponse(message = "Produto não encontrado", code = 404)
    })
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@RequestParam Integer id){
        produtoRepository.findById(id).map( produto -> {
            produtoRepository.delete(produto);
            return true;
        }).orElseThrow(() -> new ObjetoNaoEncontradoException("Produto não encontrado"));
    }

    @ApiOperation("Listar todos os produtos")
    @ApiResponse(message = "Lista de produtos", code = 200)
    @GetMapping("listar/")
    @ResponseStatus(OK)
    public List<Produto> listarProdutos(){
        return produtoRepository.findAll();
    }


}
