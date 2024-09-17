package PetroTodor.BCrypt_Authorization_._CORS.controllers;

import PetrovTodor.Spring_Security_._JWT.exceptions.BadRequestException;
import PetrovTodor.Spring_Security_._JWT.exceptions.NotFoundException;
import PetrovTodor.Spring_Security_._JWT.payload.DipendenteDto;
import PetrovTodor.Spring_Security_._JWT.payload.DipendenteLoginDto;
import PetrovTodor.Spring_Security_._JWT.payload.responsPayload.DipendenteLoginRespoDto;
import PetrovTodor.Spring_Security_._JWT.payload.responsPayload.DipendenteResponseDto;
import PetrovTodor.Spring_Security_._JWT.services.AuthService;
import PetrovTodor.Spring_Security_._JWT.services.DipendenteService;
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
