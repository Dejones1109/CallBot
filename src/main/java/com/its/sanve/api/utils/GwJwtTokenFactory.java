package com.its.sanve.api.utils;

import com.its.sanve.api.exceptions.AuthenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class GwJwtTokenFactory {
    private static final int AUTHEN_FAIL = 1;
    @Autowired
    private MessageUtils messageUtils;

    public String geterateToken(Client client) throws Exception {
        String token = null;
        Map<String, Object> claims = buildClaims(client);
        token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, client.getSecretKey().getBytes()).compact();
        log.info(token);
        return token;
    }

    private Map<String, Object> buildClaims(Client client) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("exp", buildExpireTime(client.getTokenExpireTimeMs()));
        claims.put("iss", client.getIss());
        return claims;
    }

    private long buildExpireTime(long expirationMs) {

        return System.currentTimeMillis() + expirationMs;
    }

    public boolean validateJwt(Client client, String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(client.getSecretKey().getBytes())
                    .parseClaimsJws(token).getBody();
            String iss_ = (String) claims.get("iss");
            if (!client.getIss().equalsIgnoreCase(iss_)) {
                return false;
            }
            long exp = Long.valueOf(String.valueOf(claims.get("exp")));
            if(System.currentTimeMillis() <= exp){
                return true;
            }else{
                log.info("Token expired: {}, {}", token, client.getApiKey());
                return false;
            }
        } catch (ExpiredJwtException eex) {
            log.info("Token expired: {}, {}", token, eex.getMessage());
            throw new AuthenException(
                   AUTHEN_FAIL , messageUtils.getMessage(MessageUtils.TOKEN_EXPIRED));

        } catch (Exception e) {
            log.warn("Parse token " + token + ", " + client, e);
            throw new AuthenException(
                    AUTHEN_FAIL, messageUtils.getMessage(MessageUtils.ERR_UNAUTHORIZED));
        }


    }

}
