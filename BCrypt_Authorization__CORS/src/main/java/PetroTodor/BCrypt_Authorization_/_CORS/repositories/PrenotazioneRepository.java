package PetroTodor.BCrypt_Authorization_._CORS.repositories;


import PetrovTodor.Spring_Security_._JWT.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {

    @Query("SELECT COUNT(p) FROM Prenotazione p WHERE p.viaggio.dipendente.idDipendente = :dipendenteId AND p.dataPrenotazione = :dataPrenotazione")
    long countConflictingPrenotazioni(@Param("dipendenteId") UUID dipendenteId, @Param("dataPrenotazione") LocalDate dataPrenotazione);
}
