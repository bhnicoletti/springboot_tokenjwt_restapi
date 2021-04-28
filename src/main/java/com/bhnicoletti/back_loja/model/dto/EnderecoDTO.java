package com.bhnicoletti.back_loja.model.dto;

import com.bhnicoletti.back_loja.model.UF;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EnderecoDTO {
    private Integer id;

    @NotNull(message = "{endereco.cep.invalido}")
    private Integer cep;

    @NotEmpty(message = "{endereco.logradouro.invalido}")
    private String logradouro;

    private String numero;

    @NotEmpty(message = "{endereco.bairro.invalido}")
    private String bairro;

    @NotEmpty(message = "{endereco.cidade.invalido}")
    private String cidade;

    private String complemento;

    @NotNull(message = "{endereco.uf.invalido}")
    private UF uf;
}
