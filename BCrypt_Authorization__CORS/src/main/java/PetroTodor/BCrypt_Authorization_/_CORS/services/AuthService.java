package PetroTodor.BCrypt_Authorization_._CORS.services;

import PetroTodor.BCrypt_Authorization_._CORS.entities.Dipendente;
import PetroTodor.BCrypt_Authorization_._CORS.exceptions.UnauthorizedException;
import PetroTodor.BCrypt_Authorization_._CORS.payload.DipendenteLoginDto;
import PetroTodor.BCrypt_Authorization_._CORS.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String controlloCredenzialiAndGenerazioneToken(DipendenteLoginDto body) {
        Dipendente found = dipendenteService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {

            return jwtTools.creatToken(found);
        } else {
            throw new UnauthorizedException("Credenziali Errate!");
        }

    }
}
