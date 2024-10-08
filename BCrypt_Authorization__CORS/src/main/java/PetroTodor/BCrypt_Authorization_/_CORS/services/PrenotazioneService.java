package PetroTodor.BCrypt_Authorization_._CORS.services;


import PetroTodor.BCrypt_Authorization_._CORS.entities.Dipendente;
import PetroTodor.BCrypt_Authorization_._CORS.entities.Prenotazione;
import PetroTodor.BCrypt_Authorization_._CORS.entities.Viaggio;
import PetroTodor.BCrypt_Authorization_._CORS.exceptions.BadRequestException;
import PetroTodor.BCrypt_Authorization_._CORS.exceptions.NotFoundException;
import PetroTodor.BCrypt_Authorization_._CORS.payload.PrenotazioneDto;
import PetroTodor.BCrypt_Authorization_._CORS.repositories.DipendenteRepository;
import PetroTodor.BCrypt_Authorization_._CORS.repositories.PrenotazioneRepository;
import PetroTodor.BCrypt_Authorization_._CORS.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrenotazioneService {
    @Autowired
    PrenotazioneRepository prenotazioneRepository;
    @Autowired
    ViaggioRepository viaggioRepository;
    @Autowired
    DipendenteRepository dipendenteRepository;


    public Prenotazione savePrenotazione(PrenotazioneDto body) {
        Viaggio viaggio = this.viaggioRepository.findById(body.idViaggio())
                .orElseThrow(() -> new NotFoundException(body.idViaggio()));

        Dipendente dipendente = this.dipendenteRepository.findById(body.idDipendente())
                .orElseThrow(() -> new NotFoundException(body.idDipendente()));
        viaggio.setDipendente(dipendente);
        if (dipendente == null) {
            throw new BadRequestException("Il viaggio non è associato a nessun dipendente.");
        }
        long conflitti = prenotazioneRepository.countConflictingPrenotazioni(
                dipendente.getIdDipendente(),
                body.dataPrenotazione()
        );

        if (conflitti > 0) {
            throw new BadRequestException("Il dipendente è già impegnato in questa data.");
        }
        Prenotazione prenotazione = new Prenotazione(body.dataPrenotazione(), body.noteEoPreferenze(), viaggio, dipendente);

        return this.prenotazioneRepository.save(prenotazione);
    }

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione findById(UUID idPrenotazione) {
        return this.
                prenotazioneRepository.
                findById(idPrenotazione).
                orElseThrow(()
                        -> new NotFoundException(idPrenotazione));
    }

    public Prenotazione findAndUpdite(UUID idPrenotazione, PrenotazioneDto body) {
        Viaggio viaggio = this.viaggioRepository.findById(body.idViaggio())
                .orElseThrow(() -> new NotFoundException(body.idViaggio()));

        Dipendente dipendente = this.dipendenteRepository.findById(body.idDipendente())
                .orElseThrow(() -> new NotFoundException(body.idDipendente()));
        viaggio.setDipendente(dipendente);
        if (dipendente == null) {
            throw new BadRequestException("Il viaggio non è associato a nessun dipendente.");
        }
        long conflitti = prenotazioneRepository.countConflictingPrenotazioni(
                dipendente.getIdDipendente(),
                body.dataPrenotazione()
        );
        if (conflitti > 0) {
            throw new BadRequestException("Il dipendente è già impegnato in questa data.");
        }
        Prenotazione prenotazione = findById(idPrenotazione);
        prenotazione.setDataPrenotazione(body.dataPrenotazione());
        prenotazione.setViaggio(viaggio);
        prenotazione.setDipendente(dipendente);
        prenotazione.setNoteEoPreferenze(body.noteEoPreferenze());
        return this.prenotazioneRepository.save(prenotazione);
    }

    public void findAndDelete(UUID idPrenotazione) {
        Prenotazione prenotazione = findById(idPrenotazione);
        this.prenotazioneRepository.delete(prenotazione);
    }
}
