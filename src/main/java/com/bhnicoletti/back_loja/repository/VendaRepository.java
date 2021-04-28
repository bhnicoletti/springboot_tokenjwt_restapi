package com.bhnicoletti.back_loja.repository;

import com.bhnicoletti.back_loja.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {

    List<Venda> findByClienteEmailCliente(String email);
}
