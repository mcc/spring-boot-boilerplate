package mcc.springbootboilerplate.service;

import mcc.springbootboilerplate.exception.UserAlreadyExistException;
import org.springframework.stereotype.Service;


public interface MyUserService {
    public String register(String username, CharSequence password) throws UserAlreadyExistException;
    public void increaseFailLoginAttempt(String username);

    void resetFailLoginAttempt(String username);
}
