package PetroTodor.BCrypt_Authorization_._CORS.payload;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record PrenotazioneDto(
        @NotNull(message = "L'ID del viaggio è obbligatorio!")
        UUID idViaggio,
        @NotNull(message = "L'ID del dipendente è obbligatorio!")
        UUID idDipendente,
        @NotNull(message = "Il campo Data è obbligatorio!")
        LocalDate dataPrenotazione,
        String noteEoPreferenze) {
}
