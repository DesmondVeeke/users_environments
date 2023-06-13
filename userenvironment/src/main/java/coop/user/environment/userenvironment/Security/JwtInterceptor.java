package coop.user.environment.userenvironment.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String getJwtSecret(){
        return jwtSecret;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        // Check if it's a CORS pre-flight request (OPTIONS)
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
            return true;
        }


        String authorizationHeader = request.getHeader("Authorization");
        String coopHeader = request.getHeader("COOP");

        if (coopHeader != null && coopHeader.equalsIgnoreCase("TRUE")) {
            return true;
        }

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (validateToken(token)) {
                return true;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getJwtSecret()).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
