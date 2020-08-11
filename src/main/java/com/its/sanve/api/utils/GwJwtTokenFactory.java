package com.its.sanve.api.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.its.sanve.api.exceptions.AuthenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class GwJwtTokenFactory {
    private static final int AUTHEN_FAIL = 1;
    @Autowired
    private MessageUtils messageUtils;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date now = new Date();

    public String geterateToken(Client client) throws Exception {
        String token = null;
        Map<String, Object> claims = buildClaims(client);
        token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, client.getSecretKey().getBytes()).compact();
        log.info(token);
        return token;
    }

    private Map<String, Object> buildClaims(Client client) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("creat_at", client.getCreat_at());
        return claims;
    }

    public boolean validateJwt(Client client, String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(client.getSecretKey().getBytes())
                    .parseClaimsJws(token).getBody();
            String created_at = (String) claims.get("creat_at");
            if(!client.getCreat_at().equalsIgnoreCase(created_at)) {
                return false;
            }else{
                return  true;
            }

        } catch (ExpiredJwtException eex) {
            log.info("Token false: {}, {}", token, eex.getMessage());
            throw new AuthenException(
                    AUTHEN_FAIL, messageUtils.getMessage(MessageUtils.TOKEN_EXPIRED));

        } catch (Exception e) {
            log.warn("Parse token " + token + ", " + client, e);
            throw new AuthenException(
                    AUTHEN_FAIL, messageUtils.getMessage(MessageUtils.ERR_UNAUTHORIZED));
        }


    }

    public static void main(String[] args) {


        Map<String, Object> claims = new HashMap<>();
        claims.put("creat_at","20200808");
        claims.put("api_key","testanvui");
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, "SVRzQDIwMTk=".getBytes()).compact();
        System.out.println(token);
    }

}
