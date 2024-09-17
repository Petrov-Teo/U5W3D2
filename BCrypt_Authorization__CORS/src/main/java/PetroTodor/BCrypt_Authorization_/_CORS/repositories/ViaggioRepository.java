package PetroTodor.BCrypt_Authorization_._CORS.repositories;


import PetroTodor.BCrypt_Authorization_._CORS.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, UUID> {

}
