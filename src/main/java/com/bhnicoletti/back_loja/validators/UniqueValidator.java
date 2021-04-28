package com.bhnicoletti.back_loja.validators;

import com.bhnicoletti.back_loja.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public boolean isValid(String dado, ConstraintValidatorContext constraintValidatorContext) {

        var documento = dado.replaceAll("[^0-9]+", "");

        if (documento.length() == 11 || documento.length() == 14) {
            return !clienteRepository.existsClienteByCpfCnpj(dado);
        }

        else {return !clienteRepository.existsClienteByEmailCliente(dado);
        }



    }
}
