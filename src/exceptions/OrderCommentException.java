package exceptions;

public class OrderCommentException extends Exception {
    private String wrongComment;
    public OrderCommentException (String wrongComment, String message) {
        super(message);
        setWrongComment(wrongComment);
    }

    public void setWrongComment(String wrongComment) {
        this.wrongComment = wrongComment;
    }
}
