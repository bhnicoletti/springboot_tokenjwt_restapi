package com.bhnicoletti.back_loja.rest.controller;

import com.bhnicoletti.back_loja.model.Venda;
import com.bhnicoletti.back_loja.model.dto.VendaDTO;
import com.bhnicoletti.back_loja.service.VendaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/vendas/")
@Api("Api Vendas")
public class VendaController {

    @Autowired
    VendaService vendaService;

    @PostMapping
    @ResponseStatus(OK)
    public Venda salvar(@RequestBody @Valid VendaDTO vendaDTO){
        return vendaService.efetuarVenda(vendaDTO);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<Venda> listar(){
        return vendaService.listarTodasVendas();
    }

}
