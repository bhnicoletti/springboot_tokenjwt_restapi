package com.bhnicoletti.back_loja.repository;

import com.bhnicoletti.back_loja.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByEmailCliente(String email);

    Optional<Cliente> findByEmailClienteAndSenhaCliente(String email, String senha);

    boolean existsClienteByEmailCliente(String email);

    boolean existsClienteByCpfCnpj(String dado);
}
