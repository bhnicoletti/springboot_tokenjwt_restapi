package com.bhnicoletti.back_loja.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemVendaDTO {

    private Integer idItem;

    @NotNull(message = "{item.produto.invalido}")
    private Integer produto;

    @NotNull(message = "{item.quantidade.invalido}")
    private Integer quantVenda;

    @NotNull(message = "{item.vlrsubtotal.invalido}")
    private BigDecimal vlrSubtotal;

}
