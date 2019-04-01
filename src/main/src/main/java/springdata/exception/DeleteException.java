package springdata.exception;

public class DeleteException extends RuntimeException {
    private static final long serialVersionUID = 2L;
    public DeleteException(String s) {
        super(s);
    }
}
