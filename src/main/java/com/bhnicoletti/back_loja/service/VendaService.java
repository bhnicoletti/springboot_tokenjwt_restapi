package com.bhnicoletti.back_loja.service;

import com.bhnicoletti.back_loja.model.Venda;
import com.bhnicoletti.back_loja.model.dto.VendaDTO;
import java.util.List;


public interface VendaService {

    Venda efetuarVenda(VendaDTO vendaDTO);

    Venda exibirVenda(Integer id);

    List<Venda> listarTodasVendas();
}
