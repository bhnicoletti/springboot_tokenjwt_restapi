package com.bhnicoletti.back_loja.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfCnpjValiator implements ConstraintValidator<ValidaCPFeCNPJ, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null){
         return false;
        }
        else{
            s = s.replaceAll("[^0-9]+", "");
            if (s.length() == 11){
               return Util.isCPF(s);
            }
            else if(s.length() == 14){
                return Util.isCNPJ(s);
            }
            else {
                return false;
            }
        }

    }

}
