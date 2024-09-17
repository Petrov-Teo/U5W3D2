package PetroTodor.BCrypt_Authorization_._CORS.controllers;

import PetrovTodor.Spring_Security_._JWT.entities.Prenotazione;
import PetrovTodor.Spring_Security_._JWT.exceptions.BadRequestException;
import PetrovTodor.Spring_Security_._JWT.exceptions.NotFoundException;
import PetrovTodor.Spring_Security_._JWT.payload.PrenotazioneDto;
import PetrovTodor.Spring_Security_._JWT.payload.responsPayload.PrenotazioneResponseDto;
import PetrovTodor.Spring_Security_._JWT.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;


/*
1. GET http://localhost:3001/prenotzioni
2. POST http://localhost:3001/prenotzioni (+ body)
3. GET  http://localhost:3001/prenotzioni/{idPrenotazioni}
4. PUT http://localhost:3001/prenotzioni/{idPrenotazioni}
5. DELETE http://localhost:3001/prenotzioni/{idPrenotazioni}

 */

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    PrenotazioneService prenotazioneService;

    //1. GET http://localhost:3001/prenotzioni
    @GetMapping
    public Page<Prenotazione> findAllPrenotazioni(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idPrenotazione") String sortBy) {

        return this.prenotazioneService.findAll(page, size, sortBy);
    }

    @PostMapping
    //2. POST http://localhost:3001/prenotzioni (+ body)
    @ResponseStatus(HttpStatus.CREATED)
    public PrenotazioneResponseDto savePrenotazioni(@RequestBody @Validated PrenotazioneDto body, BindingResult validationResult) throws BadRequestException {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Controlla i seguenti errori: " + messages);
        } else {
            return new PrenotazioneResponseDto(this.prenotazioneService.savePrenotazione(body).getIdPrenotazione());
        }
    }

    //3. GET  http://localhost:3001/prenotzioni/{idPrenotazioni}
    @GetMapping("/{idPrenotazioni}")
    public Prenotazione findById(@PathVariable UUID idPrenotazioni) {
        return this.prenotazioneService.findById(idPrenotazioni);
    }

    //4. PUT http://localhost:3001/prenotzioni/{idPrenotazioni}
    @PutMapping("/{idPrenotazioni}")
    public Prenotazione findAndUpditePrenotazione(@PathVariable UUID idPrenotazione, @RequestBody PrenotazioneDto body) throws NotFoundException {
        return this.prenotazioneService.findAndUpdite(idPrenotazione, body);
    }

    //5. DELETE http://localhost:3001/prenotzioni/{idPrenotazioni}
    @DeleteMapping
    public void findAndDelete(@PathVariable UUID idPrenotazione) {
        this.prenotazioneService.findAndDelete(idPrenotazione);
    }
}
