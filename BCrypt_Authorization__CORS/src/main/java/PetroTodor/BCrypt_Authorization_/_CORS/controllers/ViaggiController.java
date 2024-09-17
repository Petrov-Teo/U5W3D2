package PetroTodor.BCrypt_Authorization_._CORS.controllers;


import PetroTodor.BCrypt_Authorization_._CORS.entities.Viaggio;
import PetroTodor.BCrypt_Authorization_._CORS.entities.enums.StatoViaggio;
import PetroTodor.BCrypt_Authorization_._CORS.exceptions.BadRequestException;
import PetroTodor.BCrypt_Authorization_._CORS.payload.ViaggioDto;
import PetroTodor.BCrypt_Authorization_._CORS.payload.responsPayload.ViaggioResponseDto;
import PetroTodor.BCrypt_Authorization_._CORS.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

/*
1. GET http://localhost:3001/viggi
2. POST http://localhost:3001/viggi (+ body)
3. GET  http://localhost:3001/viggi/{autoreId}
4. PUT http://localhost:3001/viggi/{autoreId}
5. DELETE http://localhost:3001/viggi/{autoreId}

*/
@RestController
@RequestMapping("/viaggi")
public class ViaggiController {

    @Autowired
    private ViaggioService viaggioService;

    //1. GET http://localhost:3001/viggi
    @GetMapping
    public Page<Viaggio> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "idViaggio") String sortBy) {
        return viaggioService.findAll(page, size, sortBy);
    }

    //2. POST http://localhost:3001/viaggi (+ body)
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    @ResponseStatus(HttpStatus.CREATED)
    public ViaggioResponseDto saveViaggio(@RequestBody @Validated ViaggioDto body, BindingResult validationResult) throws org.apache.coyote.BadRequestException {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Controlla i seguenti errori: " + messages);
        } else {
            return new ViaggioResponseDto(this.viaggioService.saveViaggio(body).getIdViaggio());
        }
    }

    //3. GET  http://localhost:3001/viaggi/{viaggioId}
    @GetMapping("/{idViaggio}")
    @PreAuthorize("hasAuthority('ADMIN')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    public Viaggio findById(@PathVariable UUID idViaggio) {
        return viaggioService.findById(idViaggio);
    }

    //4. PUT http://localhost:3001/viaggi/{viaggioId}
    @PutMapping("/{idViaggio}")
    @PreAuthorize("hasAuthority('ADMIN')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    public Viaggio findByIdAndUpdite(@PathVariable UUID idViaggio, @RequestParam ViaggioDto body) throws org.apache.coyote.BadRequestException {

        return this.viaggioService.findAndUpdate(idViaggio, body);
    }

    //4.a PATCH http://localhost:3001/viaggi/{viaggioId}
    @PreAuthorize("hasAuthority('ADMIN')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    @PatchMapping("/SetStato/{idViaggio}")
    public Viaggio updateViaggioStato(@PathVariable UUID idViaggio, @RequestBody StatoViaggio nuovoStato) {
        return viaggioService.setStato(idViaggio, nuovoStato);
    }

    // 5. DELETE http://localhost:3001/viaggi/{viaggioId}
    @DeleteMapping("/{idViaggio}")
    @PreAuthorize("hasAuthority('ADMIN')")// SOLO GI ADMIN POSSONO ELIMINARE LE RISORSE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDipendente(UUID idViaggio) {
        viaggioService.findAndDelete(idViaggio);
    }
}




