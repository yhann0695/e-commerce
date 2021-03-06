package br.com.product.api.productapi.modules.jwt.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private Integer id;
    private String name;
    private String email;

    public static JwtResponse getUser(Claims claims) {
        try {
            return getJwtResponse(claims);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static JwtResponse getJwtResponse(Claims claims) {
        return new ObjectMapper().convertValue(claims.get("authUser"), JwtResponse.class);
    }
}
