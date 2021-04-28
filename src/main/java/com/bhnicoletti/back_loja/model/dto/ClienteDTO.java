package com.bhnicoletti.back_loja.model.dto;

import com.bhnicoletti.back_loja.model.TipoPessoa;
import com.bhnicoletti.back_loja.validators.Unique;
import com.bhnicoletti.back_loja.validators.ValidaCPFeCNPJ;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClienteDTO {
    private Integer id;

    private String nome;

    private String email;

    private String cpf_cnpj;


}
