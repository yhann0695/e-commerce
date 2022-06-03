package br.com.product.api.productapi.modules.jwt.service;

import br.com.product.api.productapi.configuration.exception.AuthorizationException;
import br.com.product.api.productapi.modules.jwt.dto.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class JwtService {

    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    private static final String BEARER = "bearer ";

    public void validateAuthorization(String token) {
        try {
           var accessToken = extractToken(token);
           var claims = Jwts.parserBuilder()
                                   .setSigningKey(Keys.hmacShaKeyFor(apiSecret.getBytes()))
                                   .build()
                                   .parseClaimsJws(accessToken).getBody();
           var user = JwtResponse.getUser(claims);
           validateData(isEmpty(user), "The user is not valid.");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AuthorizationException("Error while trying to process the Access Token.");
        }
    }

    private String extractToken(String token) {
        validateData(isEmpty(token), "The access token was not informed.");
        boolean containsToken = token.toLowerCase().contains(BEARER);
        token = token.toLowerCase();
        return validateData(containsToken, token.replace(BEARER, Strings.EMPTY));
    }

    private String validateData(boolean verify, String response) {
        if(verify) {
            throw new AuthorizationException(response);
        }
        return response;
    }


}
