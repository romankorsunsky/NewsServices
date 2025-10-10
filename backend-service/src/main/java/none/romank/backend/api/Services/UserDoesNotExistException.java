package none.romank.backend.api.Services;

public class UserDoesNotExistException extends RuntimeException{

    public UserDoesNotExistException(String msg){
        super(msg);
    }
}
