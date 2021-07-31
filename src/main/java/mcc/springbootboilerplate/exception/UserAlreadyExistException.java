package mcc.springbootboilerplate.exception;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException(String s) {
        super(s);
    }
}
