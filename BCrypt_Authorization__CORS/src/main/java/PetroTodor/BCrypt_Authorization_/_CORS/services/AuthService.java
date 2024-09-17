package PetroTodor.BCrypt_Authorization_._CORS.services;

import PetroTodor.BCrypt_Authorization_._CORS.entities.Dipendente;
import PetroTodor.BCrypt_Authorization_._CORS.exceptions.UnauthorizedException;
import PetroTodor.BCrypt_Authorization_._CORS.payload.DipendenteLoginDto;
import PetroTodor.BCrypt_Authorization_._CORS.security.JWTTools;
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
