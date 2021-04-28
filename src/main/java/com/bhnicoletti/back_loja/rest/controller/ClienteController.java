package com.bhnicoletti.back_loja.rest.controller;

import com.bhnicoletti.back_loja.exception.ObjetoNaoEncontradoException;
import com.bhnicoletti.back_loja.model.Cliente;
import com.bhnicoletti.back_loja.model.dto.ClienteDTO;
import com.bhnicoletti.back_loja.model.dto.CredenciaisDTO;
import com.bhnicoletti.back_loja.model.dto.LoginResponseDTO;
import com.bhnicoletti.back_loja.repository.ClienteRepository;
import com.bhnicoletti.back_loja.security.JwtService;
import com.bhnicoletti.back_loja.service.impl.LoginService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/clientes/")
@Api("Api Clientes")
public class ClienteController {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @ApiOperation("Salvar Cliente")
    @ApiResponses({
            @ApiResponse(message = "Cliente Salvo", code = 201),
            @ApiResponse(message = "Erro de validação", code = 400)
    })
    @PostMapping
    @ResponseStatus(CREATED)
    public LoginResponseDTO salvar(@RequestBody @Valid Cliente cliente){
        cliente.setSenhaCliente(passwordEncoder.encode(cliente.getSenhaCliente()));
        clienteRepository.save(cliente);
        var token = jwtService.gerarToken(cliente);
        return new LoginResponseDTO(cliente.getEmailCliente(), token);
    }

    @ApiOperation("Carregar Cliente")
    @ApiResponses({
            @ApiResponse(message = "Dados Cliente", code = 200),
            @ApiResponse(message = "Cliente não encontrado", code = 404)
    })
    @GetMapping("{id}")
    public Cliente buscar(@PathVariable @ApiParam("ID do cliente") Integer id){
        return clienteRepository.findById(id).orElseThrow( () -> new ObjetoNaoEncontradoException("Cliente não encontrado"));
    }

    @ApiOperation("Atualizar Cliente")
    @ApiResponses({
            @ApiResponse(message = "Clientes Salvo", code = 200),
            @ApiResponse(message = "Cliente não encontrado", code = 404)
    })
    @PutMapping("{id}")
    @ResponseStatus(OK)
    public void atualizar( @PathVariable @ApiParam("ID do cliente") Integer id ,@RequestBody  @Valid Cliente clienteAtual){
        clienteRepository.findById(id).map(cliente -> {
            clienteAtual.setSenhaCliente(passwordEncoder.encode(cliente.getSenhaCliente()));
            clienteAtual.setIdCliente(cliente.getIdCliente());
            clienteRepository.save(clienteAtual);
            return true;
        }).orElseThrow( () -> new ObjetoNaoEncontradoException("Cliente não encontrado"));
    }

    @ApiOperation("Deletar Cliente")
    @ApiResponses({
            @ApiResponse(message = "Clientes Deletado", code = 200),
            @ApiResponse(message = "Cliente não encontrado", code = 404)
    })
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletar(@PathVariable @ApiParam("ID do cliente") Integer id){
        clienteRepository.findById(id).map(cliente -> {
            clienteRepository.delete(cliente);
            return true;
        }).orElseThrow( () -> new ObjetoNaoEncontradoException("Cliente não encontrado"));
    }

    @ApiOperation("Listar/Filtrar Cliente")
    @ApiResponse(message = "Lista de clientes", code = 200)
    @GetMapping
    public List<Cliente> filtrarClientes(ClienteDTO filtro){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        ModelMapper modelMapper = new ModelMapper();
        Cliente cliente = modelMapper.map(filtro, Cliente.class);
        Example example = Example.of(cliente, matcher);
        return clienteRepository.findAll(example);
    }

    @ApiOperation("Autenticar Login")
    @ApiResponses({
            @ApiResponse(message = "Login efetuado", code = 200),
            @ApiResponse(message = "Usuário não encontrado", code = 404)
    })
    @PostMapping("/login")
    public LoginResponseDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        Cliente cliente = Cliente.builder()
                .emailCliente(credenciais.getEmail())
                .senhaCliente(credenciais.getSenha()).build();
        UserDetails autenticar = loginService.autenticar(cliente);
        String token = jwtService.gerarToken(cliente);
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new LoginResponseDTO(cliente.getEmailCliente(), token);
    }

}
