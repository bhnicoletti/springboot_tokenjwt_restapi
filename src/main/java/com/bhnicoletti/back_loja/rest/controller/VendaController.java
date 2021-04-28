package com.bhnicoletti.back_loja.rest.controller;

import com.bhnicoletti.back_loja.model.Venda;
import com.bhnicoletti.back_loja.model.dto.VendaDTO;
import com.bhnicoletti.back_loja.service.VendaService;
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
@RequestMapping("/api/vendas/")
@Api("Api Vendas")
public class VendaController {

    @Autowired
    VendaService vendaService;

    @PostMapping
    @ResponseStatus(OK)
    @ApiResponses({
            @ApiResponse(message = "Venda efetuada", code = 200),
            @ApiResponse(message = "Erro ao realizar venda", code = 409)
    })
    @ApiOperation("Salvar venda")
    public Venda salvar(@RequestBody @Valid VendaDTO vendaDTO){
        return vendaService.efetuarVenda(vendaDTO);
    }

    @GetMapping
    @ResponseStatus(OK)
    @ApiResponse(message = "Venda efetuadas", code = 200)
    @ApiOperation("Listar vendas")
    public List<Venda> listar(){
        return vendaService.listarTodasVendas();
    }

    @PutMapping("{id}/cancelar-venda")
    @ResponseStatus(NO_CONTENT)
    @ApiResponses({
            @ApiResponse(message = "Venda cancelada", code = 200),
            @ApiResponse(message = "Venda não encontrada", code = 404)
    })
    @ApiOperation("Cancelar venda")
    public void cancelar(@RequestParam Integer id){
        vendaService.cancelarVenda(id);
    }

}
