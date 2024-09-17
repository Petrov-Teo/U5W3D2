package PetroTodor.BCrypt_Authorization_._CORS.controllers;

import PetroTodor.BCrypt_Authorization_._CORS.exceptions.BadRequestException;
import PetroTodor.BCrypt_Authorization_._CORS.exceptions.NotFoundException;
import PetroTodor.BCrypt_Authorization_._CORS.payload.DipendenteDto;
import PetroTodor.BCrypt_Authorization_._CORS.payload.DipendenteLoginDto;
import PetroTodor.BCrypt_Authorization_._CORS.payload.responsPayload.DipendenteLoginRespoDto;
import PetroTodor.BCrypt_Authorization_._CORS.payload.responsPayload.DipendenteResponseDto;
import PetroTodor.BCrypt_Authorization_._CORS.services.AuthService;
import PetroTodor.BCrypt_Authorization_._CORS.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public DipendenteLoginRespoDto login(@RequestBody DipendenteLoginDto payload) {
        return new DipendenteLoginRespoDto(this.authService.controlloCredenzialiAndGenerazioneToken(payload));

    }

    //2. POST http://localhost:3001/dipendenti (+ body)
    @PostMapping("/register")
    
    @ResponseStatus(HttpStatus.CREATED)
    public DipendenteResponseDto saveDipendente(@RequestBody @Validated DipendenteDto body, BindingResult validationResult) throws NotFoundException, org.apache.coyote.BadRequestException {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Controlla i seguenti errori: " + messages);
        } else {
            return new DipendenteResponseDto(this.dipendenteService.saveDipendente(body).getIdDipendente());
        }
    }
}
