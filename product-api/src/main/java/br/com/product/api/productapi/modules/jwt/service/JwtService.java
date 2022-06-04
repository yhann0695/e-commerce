package br.com.product.api.productapi.modules.jwt.service;

import br.com.product.api.productapi.configuration.exception.AuthorizationException;
import br.com.product.api.productapi.modules.jwt.dto.JwtResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class JwtService {

    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;

    public void validateAuthorization(String token) {
        var accessToken = extractToken(token);
        try {
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

        if(token.contains(EMPTY_SPACE))
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        return token;
    }

    private void validateData(boolean verify, String response) {
        if(verify)
            throw new AuthorizationException(response);

    }


}
