package com.bhnicoletti.back_loja.repository;

import com.bhnicoletti.back_loja.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    List<Endereco> findByClienteEmailCliente(String email);

}
