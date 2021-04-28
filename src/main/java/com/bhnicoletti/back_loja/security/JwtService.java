package com.bhnicoletti.back_loja.security;

import com.bhnicoletti.back_loja.model.Cliente;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;


    public String gerarToken(Cliente cliente){
        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now()
                .plusMinutes(expString); // adiciona a quantidade de tempo em cima da hora atual
        Date data = Date.from(dataHoraExpiracao.
                atZone(ZoneId.systemDefault()).toInstant()); // converte o localdate para data

        return Jwts.builder()
                .setSubject(cliente.getEmailCliente()) //seto a informação do payload
                .setExpiration(data) // seta a data de expiracao
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura) // setando o algoritmo de encode e a chave
                .compact();// gera o token
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return  Jwts.parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token){
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        }catch (Exception e){
            return false;
        }
    }

    public String obterEmail(String token) throws ExpiredJwtException{
        return (String) obterClaims(token).getSubject();
    }
}
