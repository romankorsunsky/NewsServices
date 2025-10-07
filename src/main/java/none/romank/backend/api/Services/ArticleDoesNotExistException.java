package none.romank.backend.api.Services;

public class ArticleDoesNotExistException extends RuntimeException{
    public ArticleDoesNotExistException(String msg){
        super("Article Doesn't Exist!");
    }
}
