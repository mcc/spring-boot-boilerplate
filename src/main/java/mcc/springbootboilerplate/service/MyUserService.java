package mcc.springbootboilerplate.service;

import mcc.springbootboilerplate.entity.MyUser;
import mcc.springbootboilerplate.exception.UserAlreadyExistException;
import org.springframework.stereotype.Service;


public interface MyUserService {
    public MyUser getById(Long id);
    public String register(String username, CharSequence password) throws UserAlreadyExistException;
    public Boolean unlock(Long id);
    public void increaseFailLoginAttempt(String username);

    void resetFailLoginAttempt(String username);
}
