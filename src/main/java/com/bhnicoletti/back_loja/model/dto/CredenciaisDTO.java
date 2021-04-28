package com.bhnicoletti.back_loja.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CredenciaisDTO {
    private String email;
    private String senha;
}
