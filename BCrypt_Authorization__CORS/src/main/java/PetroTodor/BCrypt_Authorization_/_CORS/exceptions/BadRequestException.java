package PetroTodor.BCrypt_Authorization_._CORS.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String st) {
        super("Verifica i campi inseriti err-400" + st + ":");
    }
}
