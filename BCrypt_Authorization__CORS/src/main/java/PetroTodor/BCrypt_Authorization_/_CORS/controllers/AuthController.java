package PetroTodor.BCrypt_Authorization_._CORS.controllers;

import PetroTodor.BCrypt_Authorization_._CORS.payload.DipendenteLoginDto;
import PetroTodor.BCrypt_Authorization_._CORS.payload.responsPayload.DipendenteLoginRespoDto;
import PetroTodor.BCrypt_Authorization_._CORS.services.AuthService;
import PetroTodor.BCrypt_Authorization_._CORS.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    //2. POST http://localhost:3001/dipendenti (+ body)
//    @PostMapping("/register")
//
//    @ResponseStatus(HttpStatus.CREATED)
//    public DipendenteResponseDto saveDipendente(@RequestBody @Validated DipendenteDto body, BindingResult validationResult) throws NotFoundException, org.apache.coyote.BadRequestException {
//        if (validationResult.hasErrors()) {
//            String messages = validationResult.getAllErrors().stream()
//                    .map(objectError -> objectError.getDefaultMessage())
//                    .collect(Collectors.joining(". "));
//            throw new BadRequestException("Controlla i seguenti errori: " + messages);
//        } else {
//            return new DipendenteResponseDto(this.dipendenteService.saveDipendente(body).getIdDipendente());
//        }
//    }
}
