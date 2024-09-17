package PetroTodor.BCrypt_Authorization_._CORS.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ViaggioDto(
        @NotEmpty(message = "Il campo destinazione è obbligatorio!")
        String destinazione,
        @NotNull(message = "il campo data  obbligatorio!")
        LocalDate data,
        UUID idDipendente) {
}
