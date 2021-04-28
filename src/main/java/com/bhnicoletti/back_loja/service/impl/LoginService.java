package com.bhnicoletti.back_loja.service.impl;

import com.bhnicoletti.back_loja.exception.SenhaInvalidaException;
import com.bhnicoletti.back_loja.model.Cliente;
import com.bhnicoletti.back_loja.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    public ClienteRepository clienteRepository;

    @Autowired
    public PasswordEncoder encoder;

    public UserDetails autenticar(Cliente cliente) {
        UserDetails user = loadUserByUsername(cliente.getEmailCliente());
        boolean checkpassword = encoder.matches(cliente.getSenhaCliente(), user.getPassword());
        if (checkpassword) {
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String emailCliente) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmailCliente(emailCliente)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String[] roles = new String[] {"ADMIN", "USER"};

        return User.builder()
                .username(cliente.getEmailCliente())
                .password(cliente.getSenhaCliente())
                .roles(roles)
                .build();
    }
}
