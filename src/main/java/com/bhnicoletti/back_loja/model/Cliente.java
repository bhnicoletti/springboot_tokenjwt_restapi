package com.bhnicoletti.back_loja.model;

import com.bhnicoletti.back_loja.validators.Unique;
import com.bhnicoletti.back_loja.validators.ValidaCPFeCNPJ;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @Column(name = "nome_cliente", nullable = false)
    @NotEmpty(message = "{cliente.nomeCliente.invalido}")
    private String nomeCliente;

    @Column(name = "data_cadastro_cliente")
    private String dataCadastroCliente;

    @Column(name = "email_cliente", nullable = false, unique = true)
    @NotEmpty(message = "{cliente.emailCliente.invalido}")
    @Unique(message = "{cliente.emailCliente.unico}")
    @Email(message = "Email inválido")
    private String emailCliente;

    @Column(name = "senha_cliente", nullable = false)
    @NotEmpty(message = "{cliente.senhaCliente.invalido}")
    private String senhaCliente;

    @Column(name = "tipo_pessoa")
    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    @Column(name = "cpf_cnpj", unique = true)
    @Unique(message = "{cliente.cpjcnpj.unico}")
    @ValidaCPFeCNPJ(message = "{cliente.cpfcnpj.invalido}")
    private String cpfCnpj;

    @PrePersist
    private void prePersist(){
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.dataCadastroCliente = LocalDateTime.now().format(formatter);
    }
}




