package com.bhnicoletti.back_loja.repository;

import com.bhnicoletti.back_loja.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Integer> {
}
