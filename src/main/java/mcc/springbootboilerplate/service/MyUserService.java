package mcc.springbootboilerplate.service;

import mcc.springbootboilerplate.entity.MyUser;
import mcc.springbootboilerplate.exception.UserAlreadyExistException;
import org.springframework.stereotype.Service;


public interface MyUserService {
    MyUser enable(Long id);

    MyUser disable(Long id);

    MyUser passwordExpired(Long id);

    MyUser passwordNotExpired(Long id);

    public MyUser getById(Long id);
    public MyUser register(String username, CharSequence password) throws UserAlreadyExistException;
    public MyUser unlock(Long id);
    public void increaseFailLoginAttempt(String username);

    void resetFailLoginAttempt(String username);
}
