package PetroTodor.BCrypt_Authorization_._CORS.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "dipendenti")
public class Dipendente {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID idDipendente;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String avatarUrl;


    public Dipendente(String username, String nome, String cognome, String email, String password, String avatarUrl) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }
}
