package com.bhnicoletti.back_loja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venda")
    private Integer idVenda;

    @Column(name = "data_venda", updatable = false)
    private String dataVenda;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @NotNull(message = "{venda.cliente.invalido}")
    @JsonIgnore
    private Cliente cliente;

    @OneToMany(mappedBy = "venda", fetch = FetchType.EAGER)
    @NotNull(message = "{venda.itens.invalido}")
    private List<ItemVenda> itens;

    @Column(name = "valor_total", nullable = false, precision = 20, scale = 2)
    @NotNull(message = "{venda.vlrTotal.invalido}")
    private BigDecimal vlrTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_venda")
    private StatusVenda statusVenda;

    @PrePersist
    private void prePersist(){
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.dataVenda = LocalDateTime.now().format(formatter);
    }


}
