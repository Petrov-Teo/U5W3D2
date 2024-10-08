package PetroTodor.BCrypt_Authorization_._CORS.services;


import PetroTodor.BCrypt_Authorization_._CORS.entities.Dipendente;
import PetroTodor.BCrypt_Authorization_._CORS.entities.Viaggio;
import PetroTodor.BCrypt_Authorization_._CORS.entities.enums.StatoViaggio;
import PetroTodor.BCrypt_Authorization_._CORS.exceptions.BadRequestException;
import PetroTodor.BCrypt_Authorization_._CORS.exceptions.NotFoundException;
import PetroTodor.BCrypt_Authorization_._CORS.payload.ViaggioDto;
import PetroTodor.BCrypt_Authorization_._CORS.repositories.DipendenteRepository;
import PetroTodor.BCrypt_Authorization_._CORS.repositories.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ViaggioService {
    @Autowired
    ViaggioRepository viaggioRepository;
    ;

    @Autowired
    DipendenteRepository dipendenteRepository;


    public Viaggio saveViaggio(ViaggioDto body) {
        Viaggio viaggio = new Viaggio(body.destinazione(), body.data());
        return viaggioRepository.save(viaggio);
    }


    public Viaggio setStato(UUID idViaggio, StatoViaggio nuovoStato) {
        Viaggio viaggio = this.viaggioRepository.findById(idViaggio)
                .orElseThrow(() -> new NotFoundException(idViaggio));

        if (viaggio.getDipendente() == null) {
            throw new BadRequestException("Non puoi cambiare lo stato, non è stato assegnato nessun dipendente");
        }
        viaggio.setStato(nuovoStato);
        return viaggioRepository.save(viaggio);
    }

    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.viaggioRepository.findAll(pageable);
    }

    public Viaggio findById(UUID idViaggio) {
        return this.viaggioRepository.findById(idViaggio).orElseThrow(() -> new NotFoundException(idViaggio));
    }

    public void findAndDelete(UUID idViaggio) {
        Viaggio found = this.findById(idViaggio);
        this.viaggioRepository.delete(found);
    }

    public Viaggio findAndUpdate(UUID idViaggio, ViaggioDto body) throws BadRequestException {
        UUID dipendenteId = body.idDipendente();

        Dipendente dipendente = this.dipendenteRepository.findById(dipendenteId)
                .orElseThrow(() -> new NotFoundException(dipendenteId));
        Viaggio found = this.findById(idViaggio);
        found.setData(body.data());
        found.setDipendente(dipendente);
        found.setDestinazione(body.destinazione());
        return this.viaggioRepository.save(found);
    }
}

