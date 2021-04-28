package com.bhnicoletti.back_loja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private Integer idItem;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    @NotNull(message = "{item.produto.invalido}")
    private Produto produto;

    @Column(name = "quantidade_venda", nullable = false)
    @NotNull(message = "{item.quantidade.invalido}")
    private Integer quantVenda;

    @Column(name = "valor_sub_total", precision = 20, scale = 2, nullable = false)
    @NotNull(message = "{item.vlrsubtotal.invalido}")
    private BigDecimal vlrSubtotal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_venda")
    private Venda venda;
}
