package com.bhnicoletti.back_loja.service.impl;

import com.bhnicoletti.back_loja.exception.ObjetoNaoEncontradoException;
import com.bhnicoletti.back_loja.exception.ProdutoSemEstoqueException;
import com.bhnicoletti.back_loja.model.ItemVenda;
import com.bhnicoletti.back_loja.model.StatusVenda;
import com.bhnicoletti.back_loja.model.Venda;
import com.bhnicoletti.back_loja.model.dto.VendaDTO;
import com.bhnicoletti.back_loja.repository.ClienteRepository;
import com.bhnicoletti.back_loja.repository.ItemVendaRepository;
import com.bhnicoletti.back_loja.repository.ProdutoRepository;
import com.bhnicoletti.back_loja.repository.VendaRepository;
import com.bhnicoletti.back_loja.service.EstoqueService;
import com.bhnicoletti.back_loja.service.VendaService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaServiceIMPL implements VendaService {

    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @Autowired
    EstoqueService estoqueService;

    @Transactional
    @Override
    public Venda efetuarVenda(VendaDTO vendaDTO){
        Venda venda = new Venda();
        venda.setVlrTotal(vendaDTO.getVlrTotal());
        var cliente = clienteRepository.findById(vendaDTO.getCliente()).orElseThrow( () -> new ObjetoNaoEncontradoException("Cliente não encontrado"));
        venda.setCliente(cliente);
        var itens = vendaDTO.getItens().stream().map( itemVendaDTO -> {
                                        var item = new ItemVenda();
                                        var produto = produtoRepository.findById(itemVendaDTO.getProduto()).orElseThrow( () -> new ObjetoNaoEncontradoException("Produto não encontrado"));
                                        item.setVenda(venda);
                                        item.setQuantVenda(itemVendaDTO.getQuantVenda());
                                        item.setVlrSubtotal(itemVendaDTO.getVlrSubtotal());
                                        if (produto.getEstoqueProduto() - item.getQuantVenda() < 0){
                                            throw new ProdutoSemEstoqueException("Ops...ficamos sem estoque");
                                        }
                                        item.setProduto(estoqueService.baixaEstoque(produto, item.getQuantVenda()));
                                        itemVendaRepository.save(item);
                                        return item;
                                    }).collect(Collectors.toList());
        venda.setStatusVenda(StatusVenda.EFETUADA);
        venda.setItens(itens);

        return vendaRepository.save(venda);
    }

    @Override
    public Venda exibirVenda(Integer id){
       return vendaRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Venda não encontrada"));
    }

    @Override
    public List<Venda> listarTodasVendas(){
        return vendaRepository.findByClienteEmailCliente(getFromToken());
    }

    @Override
    public void cancelarVenda(Integer id) {
        try {
            Venda venda = vendaRepository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Venda não encontrada"));
            venda.setStatusVenda(StatusVenda.CANCELADA);
            vendaRepository.save(venda);
        }
        catch (Exception ex){
            throw new ServiceException("Erro: "+ex.getMessage());
        }

    }

    private String getFromToken(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        return email;
    }
}
