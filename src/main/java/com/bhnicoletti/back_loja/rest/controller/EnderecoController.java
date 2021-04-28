package com.bhnicoletti.back_loja.rest.controller;

import com.bhnicoletti.back_loja.exception.ObjetoNaoEncontradoException;
import com.bhnicoletti.back_loja.model.Cliente;
import com.bhnicoletti.back_loja.model.Endereco;
import com.bhnicoletti.back_loja.model.dto.EnderecoDTO;
import com.bhnicoletti.back_loja.repository.ClienteRepository;
import com.bhnicoletti.back_loja.repository.EnderecoRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/enderecos/")
@Api("Api Endereços")
public class EnderecoController {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    ClienteRepository clienteRepository;


    @ApiOperation("Salvar novo endereço")
    @ApiResponses({
            @ApiResponse(message = "Endereço Salvo", code = 201),
            @ApiResponse(message = "Erro de validação", code = 400)
    })
    @PostMapping
    @ResponseStatus(CREATED)
    public Endereco salvar(@RequestBody @Valid EnderecoDTO end){
        Cliente cliente = clienteRepository.findByEmailCliente(getFromToken()).orElseThrow( () -> new ObjetoNaoEncontradoException("Cliente não encontrado"));
        ModelMapper modelMapper = new ModelMapper();
        Endereco endereco = modelMapper.map(end, Endereco.class);
        endereco.setCliente(cliente);
        enderecoRepository.save(endereco);
        return endereco;
    }

    @ApiOperation("Atualizar endereço")
    @ApiResponses({
            @ApiResponse(message = "Endereço Salvo", code = 200),
            @ApiResponse(message = "Erro de validação", code = 400)
    })
    @PutMapping("{id}")
    @ResponseStatus(OK)
    public void atualizar(@RequestBody @Valid EnderecoDTO end, @PathVariable @ApiParam("ID do endereço") Integer id){
        ModelMapper modelMapper = new ModelMapper();
        Cliente cliente = clienteRepository.findByEmailCliente(getFromToken()).orElseThrow( () -> new ObjetoNaoEncontradoException("Cliente não encontrado"));
        enderecoRepository.findById(id).map( endereco -> {
            endereco = modelMapper.map(end, Endereco.class);
            endereco.setCliente(cliente);
            enderecoRepository.save(endereco);
            return endereco;
        }).orElseThrow(() -> new ObjetoNaoEncontradoException("Endereço não encontrado"));
    }

    @ApiOperation("Carregar Endereço")
    @ApiResponses({
            @ApiResponse(message = "Dados Endereço", code = 200),
            @ApiResponse(message = "Endereço não encontrado", code = 404)
    })
    @GetMapping("{id}")
    @ResponseStatus(OK)
    public EnderecoDTO buscar(@PathVariable @ApiParam("ID do Endereço") Integer id){
        Endereco endereco = enderecoRepository.findById(id).orElseThrow( () -> new ObjetoNaoEncontradoException("Endereço não encontrado"));
        ModelMapper modelMapper = new ModelMapper();
        EnderecoDTO enderecoDTO = modelMapper.map(endereco, EnderecoDTO.class);
        return enderecoDTO;
    }

    @ApiOperation("Deletar Endereço")
    @ApiResponses({
            @ApiResponse(message = "Endereço Deletado", code = 204),
            @ApiResponse(message = "Endereço não encontrado", code = 404)
    })
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable @ApiParam("ID do Endereço") Integer id){
        enderecoRepository.findById(id).map(endereco -> {
            enderecoRepository.delete(endereco);
            return true;
        }).orElseThrow( () -> new ObjetoNaoEncontradoException("Endereço não encontrado"));
    }

    @ApiOperation("Buscar Endereço por cliente")
    @ApiResponse(message = "Lista de endereços", code = 200)
    @GetMapping("por-cliente/")
    public List<EnderecoDTO> buscarEnderecosPorCliente(){

        ModelMapper modelMapper = new ModelMapper();

        List<EnderecoDTO> lista = enderecoRepository.findByClienteEmailCliente(getFromToken()).stream().map(
                    endereco -> {
                            EnderecoDTO enderecoDTO = modelMapper.map(endereco, EnderecoDTO.class);
                            return enderecoDTO;
                    }).collect(Collectors.toList());
        return lista;
    }

    private String getFromToken(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        return email;
    }
}
