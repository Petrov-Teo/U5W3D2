package PetroTodor.BCrypt_Authorization_._CORS.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DipendenteDto(
        @NotEmpty(message = "il Campo è obbligatorio! Minimo 5 caratteri")
        @Size(min = 5)
        String username,
        @NotEmpty(message = "il Campo è obbligatorio! Minimo 2 caratteri")
        @Size(min = 2)
        String nome,
        @NotEmpty(message = "il Campo è obbligatorio! Minimo 2 caratteri")
        @Size(min = 2)
        String cognome,
        @NotEmpty(message = "il Campo è obbligatorio!")
        @Email(message = "Verifica la mail!")
        String email,
        @NotEmpty(message = "il campo password è obbligatorio!")
        String password) {
}
