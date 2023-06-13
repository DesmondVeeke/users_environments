package coop.user.environment.userenvironment.Services;

import coop.user.environment.userenvironment.Entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String getJwtSecret(){
        return jwtSecret;
    }

    public String generateJwtToken(User user) {

        long expirationTimeMillis = System.currentTimeMillis() + (60 * 60 * 1000); // 1 hour

        String user_id_string = user.getId().toString();

        return Jwts.builder()
                .setSubject(user_id_string)
                .setExpiration(new Date(expirationTimeMillis))
                .signWith(SignatureAlgorithm.HS512, this.getJwtSecret())
                .compact();
    }
}
