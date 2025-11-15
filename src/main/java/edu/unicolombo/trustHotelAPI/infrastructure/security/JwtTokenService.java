package edu.unicolombo.trustHotelAPI.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.unicolombo.trustHotelAPI.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtTokenService {

    @Value("${api.security.secret}")
    private String SECRET_KEY;

    @Value("${api.security.expirationMs}")
    private long expirationMs;

    private Algorithm algorithm(){
        return Algorithm.HMAC256(SECRET_KEY);
    }


    public String generateToken(User user){
        var expiry = generateExpirationDate();
        try{
            return JWT.create()
                    .withIssuer("trust hotel api")
                    .withSubject(user.getUsername())
                    .withClaim("user", user.getName())
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(expiry)
                    .sign(algorithm());
        } catch (JWTCreationException exception){
            // invalid signing configuration // Couldn't convert claim
            throw new RuntimeException(exception.getMessage());
        }
    }

    public String extractUsername(String token){
        return decode(token).getSubject();
    }

    public boolean validateToken(String token){
        try {
            JWT.require(algorithm()).build().verify(token);
            return true;
        } catch (JWTVerificationException exp){
            return false;
        }
    }
    private DecodedJWT decode(String token){
        return JWT.require(algorithm()).build().verify(token);
    }

    private  Instant generateExpirationDate(){
        Date now = new Date();
        return new Date(now.getTime() + expirationMs).toInstant();
    }
}
