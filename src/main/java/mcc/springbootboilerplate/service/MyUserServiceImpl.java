package mcc.springbootboilerplate.service;

import mcc.springbootboilerplate.entity.MyLoginAttempt;
import mcc.springbootboilerplate.entity.MyUser;
import mcc.springbootboilerplate.exception.UserAlreadyExistException;
import mcc.springbootboilerplate.repository.MyLoginAttemptRepository;
import mcc.springbootboilerplate.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MyUserServiceImpl implements MyUserService {
    @Autowired
    MyUserRepository myUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MyLoginAttemptRepository myLoginAttemptRepository;

    @Override
    public MyUser register(String username, CharSequence password) throws UserAlreadyExistException {
        if(myUserRepository.findByUsername(username).isPresent()){
            throw new UserAlreadyExistException("User already exists");
        }
        MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setEncPassword(encodePassword(password));
        myUser.setFailedAttempts(0);
        myUser.setIsDisabled(0);
        myUser.setIsLocked(0);
        myUser.setLastPasswordDate(new Date());
        myUserRepository.save(myUser);
        return myUser;
    }

    @Override
    public MyUser unlock(Long id) {
        MyUser byId = myUserRepository.getById(id);
        byId.setIsLocked(0);
        byId.setFailedAttempts(0);
        return myUserRepository.save(byId);
    }

    @Override
    public MyUser enable(Long id) {
        MyUser byId = myUserRepository.getById(id);
        byId.setIsDisabled(0);
        return myUserRepository.save(byId);
    }

    @Override
    public MyUser disable(Long id) {
        MyUser byId = myUserRepository.getById(id);
        byId.setIsDisabled(1);
        return myUserRepository.save(byId);
    }

    @Override
    public MyUser passwordExpired(Long id) {
        MyUser byId = myUserRepository.getById(id);
        byId.setIsPwExpired(1);
        return myUserRepository.save(byId);
    }

    @Override
    public MyUser passwordNotExpired(Long id) {
        MyUser byId = myUserRepository.getById(id);
        byId.setIsPwExpired(0);
        return myUserRepository.save(byId);
    }



    @Override
    public MyUser getById(Long id) {
        return myUserRepository.findById(id).get();
    }

    @Override
    public void increaseFailLoginAttempt(String username) {
        Optional<MyUser> byUsername = myUserRepository.findByUsername(username);

        if (byUsername.isPresent()){
            MyUser myUser = byUsername.get();
            myUser.setFailedAttempts(myUser.getFailedAttempts() + 1);
            myUserRepository.save(myUser);
        }
        MyLoginAttempt myLoginAttempt = new MyLoginAttempt();
        myLoginAttempt.setUsername(username);
        myLoginAttempt.setRecordDate(new Date());
        myLoginAttemptRepository.save(myLoginAttempt);


    }

    @Override
    public void resetFailLoginAttempt(String username) {
        Optional<MyUser> byUsername = myUserRepository.findByUsername(username);
        MyUser myUser = byUsername.get();
        myUser.setFailedAttempts(0);
        myUserRepository.save(myUser);

    }

    private String encodePassword(CharSequence pass){
        return passwordEncoder.encode(pass);
    }
}
