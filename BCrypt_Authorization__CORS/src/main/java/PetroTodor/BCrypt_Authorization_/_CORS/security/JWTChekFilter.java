package PetroTodor.BCrypt_Authorization_._CORS.security;

import PetrovTodor.Spring_Security_._JWT.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTChekFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //E' il metodo che verrà richiamato per ogni richiesta, tranne quelle che escludiamo
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Si prega di inserire il token nell'Authorization Header!");
        String accessToken = authHeader.replace("Bearer ", "");
        System.out.println("ACCESS TOKEN " + accessToken);
        jwtTools.verifyToken(accessToken);

        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Ci serve per disabilitare alcune richieste ad esempio su determinati endpoint oppure determinati controller
        // Nel nostro caso non vengano chiamati i token in fase di login e/o registrazione
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
