package PetroTodor.BCrypt_Authorization_._CORS.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
// Se voglio dichiarare le regole di autorizzazione direttamente sui singoli endpoint, allora questa
//Annotazione è OBBLIVAGORIA!
public class Config {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(http -> http.disable());
        httpSecurity.csrf(http -> http.disable());
        httpSecurity.sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authorizeHttpRequests(http -> http.requestMatchers("/**").permitAll());
        httpSecurity.cors(Customizer.withDefaults()); // OBBLGATORIA SE VOGLIO CHE PER I CORS VENGA UTILIZZATO IL BEAN SOTTOSTANTE
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder getBCript() {
        return new BCryptPasswordEncoder(11); // 11 è il numero di rounds, ovvero quante volte viene eseguito
        //l'algoritmo, ciò è utile per regolare la velocità di esecuzione,
        //Più è lento, più saranno sicure le nostre password e ovviamente viceversa. Bisogna però tenere la considerazione
        //anche la UX, quindi più e lento, peggio sarà per i nostri utenti.
        // 11 ad esempio significa che l'algoritmo verrà eseguito 2^11 volte cioè 2048 volte
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        //whitelist con uno o più indirizzi dei frontend che voglio che accedono a questo backend.
        //Se voglio permettere l'accesso a tutti (anche se un pò rischioso) basta mettere "*"
        configuration.setAllowedMethods(Arrays.asList("*")); // se voglio determinare le richieste autorizzate GET POST ecc.
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

        //NON DIMENTICHIAMOCI CHE VA AGGIUNTA UN IMPOSTAZIONE PER I CORS ANCHE NELLA FILTER CHAIN QUA SOPRA
    }
}
