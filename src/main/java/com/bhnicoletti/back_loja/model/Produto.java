package com.bhnicoletti.back_loja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Integer idProduto;

    @Column(name = "nome_produto", nullable = false)
    @NotEmpty(message = "{produto.nomeProduto.invalido}")
    private String nomeProduto;

    @Column(name = "descricao_produto")
    private String descProduto;

    @Column(name = "valor_produto", nullable = false, precision = 20, scale = 2)
    @NotNull(message = "{produto.vlrProduto.invalido}")
    private BigDecimal vlrProduto;

    @Column(name = "estoque_produto", nullable = false)
    @NotNull(message = "{produto.estoqueProduto.invalido}")
    private Integer estoqueProduto;
}
