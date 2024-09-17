package PetroTodor.BCrypt_Authorization_._CORS.controllers;


import PetroTodor.BCrypt_Authorization_._CORS.entities.Dipendente;
import PetroTodor.BCrypt_Authorization_._CORS.exceptions.BadRequestException;
import PetroTodor.BCrypt_Authorization_._CORS.exceptions.NotFoundException;
import PetroTodor.BCrypt_Authorization_._CORS.payload.DipendenteDto;
import PetroTodor.BCrypt_Authorization_._CORS.payload.responsPayload.DipendenteResponseDto;
import PetroTodor.BCrypt_Authorization_._CORS.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

/*
1. GET http://localhost:3001/dipendenti
2. POST http://localhost:3001/dipendenti (+ body)
3. GET  http://localhost:3001/dipendenti/{autoreId}
4. PUT http://localhost:3001/dipendenti/{autoreId}
5. DELETE http://localhost:3001/dipendenti/{autoreId}


 */
@RestController
@RequestMapping("/dipendenti")

public class DipendenteController {
    @Autowired
    private DipendenteService dipendenteService;

    @GetMapping("/me")
    // Permette di prendere le info sul proprio profilo (identifica chi si è loggato, permettendo di effettuare anche delle modifiche)
    public Dipendente getProfile(@AuthenticationPrincipal Dipendente currentDipendente) {
        return currentDipendente;
    }

    @PutMapping("/me")
    // Con questo metodo abbiamo permesso all'utente di modificare il proprio profilo, ma non quello degli altri, che
    //potranno essere modificati solo dagli amministratori!
    public Dipendente putProfileDipendente(@AuthenticationPrincipal Dipendente currentDipendente, @RequestBody DipendenteDto body) throws BadRequestException, org.apache.coyote.BadRequestException {
        return this.dipendenteService.findAndUpdate(currentDipendente.getIdDipendente(), body);
    }

    @DeleteMapping("/me")
    // serve per eliminare l proprio profilo, in caso si decida, ma non quello degli altri, che potrà essere eliminato
    //dagli amministratori
    public void deleteDipendenteMe(@AuthenticationPrincipal Dipendente currentDipendente) {
        this.deleteDipendente(currentDipendente.getIdDipendente());
    }

    //1. GET http://localhost:3001/dipendenti
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    public Page<Dipendente> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "idDipendente") String sorteBy) {

        return dipendenteService.findAll(page, size, sorteBy);
    }


    //3. GET  http://localhost:3001/dipendenti/{autoreId}
    @GetMapping("{idDipendente}")
    @PreAuthorize("hasAuthority('ADMIN')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    public Dipendente findById(@PathVariable UUID idDipendente) {
        return this.dipendenteService.findById(idDipendente);
    }

    //2. POST http://localhost:3001/dipendenti (+ body)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
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

    //4. PUT http://localhost:3001/dipendenti/{autoreId}
    @PutMapping("{idDipendente}")
    @PreAuthorize("hasAuthority('ADMIN')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    public Dipendente findByIdAndUpdite(@PathVariable UUID idDipendente, @RequestParam DipendenteDto body) throws BadRequestException, org.apache.coyote.BadRequestException {

        return this.dipendenteService.findAndUpdate(idDipendente, body);
    }

    // 5. DELETE http://localhost:3001/dipendenti/{autoreId}
    @DeleteMapping("{idDipendente}")
    @PreAuthorize("hasAuthority('ADMIN')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDipendente(@PathVariable UUID idDipendente) {
        dipendenteService.findAndDelete(idDipendente);
    }


}
