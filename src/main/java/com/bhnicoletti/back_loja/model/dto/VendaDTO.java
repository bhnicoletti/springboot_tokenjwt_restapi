package com.bhnicoletti.back_loja.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VendaDTO {
    private Integer idVenda;

    @NotNull(message = "{venda.cliente.invalido}")
    private Integer cliente;

    @NotNull(message = "{venda.itens.invalido}")
    private List<ItemVendaDTO> itens;

    @NotNull(message = "{venda.vlrTotal.invalido}")
    private BigDecimal vlrTotal;
}
