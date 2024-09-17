package PetroTodor.BCrypt_Authorization_._CORS.services;

import PetrovTodor.Spring_Security_._JWT.entities.Dipendente;
import PetrovTodor.Spring_Security_._JWT.exceptions.UnauthorizedException;
import PetrovTodor.Spring_Security_._JWT.payload.DipendenteLoginDto;
import PetrovTodor.Spring_Security_._JWT.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private JWTTools jwtTools;

    public String controlloCredenzialiAndGenerazioneToken(DipendenteLoginDto body) {
        Dipendente found = dipendenteService.findByEmail(body.email());
        if (found.getPassword().equals(body.password())) {

            return jwtTools.creatToken(found);
        } else {
            throw new UnauthorizedException("Credenziali Errate!");
        }

    }
}
