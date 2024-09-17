package PetroTodor.BCrypt_Authorization_._CORS.controllers;


import PetroTodor.BCrypt_Authorization_._CORS.entities.Dipendente;
import PetroTodor.BCrypt_Authorization_._CORS.exceptions.BadRequestException;
import PetroTodor.BCrypt_Authorization_._CORS.payload.DipendenteDto;
import PetroTodor.BCrypt_Authorization_._CORS.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    //1. GET http://localhost:3001/dipendenti
    @GetMapping
    public Page<Dipendente> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "idDipendente") String sorteBy) {

        return dipendenteService.findAll(page, size, sorteBy);
    }


    //3. GET  http://localhost:3001/dipendenti/{autoreId}
    @GetMapping("{idDipendente}")
    public Dipendente findById(@PathVariable UUID idDipendente) {
        return this.dipendenteService.findById(idDipendente);
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
