package none.romank.backend;

public class BadArticleArgumentsException extends RuntimeException{
    public BadArticleArgumentsException(String msg){
        super(msg);
    }
}
