package supplobang.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException  {
    public CategoryAlreadyExistsException(String message){
        super(message);
    }
}
