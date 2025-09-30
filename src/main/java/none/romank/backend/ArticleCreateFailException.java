package none.romank.backend;

public class ArticleCreateFailException extends RuntimeException{
    public ArticleCreateFailException(String msg){
        super(msg);
    }
}
