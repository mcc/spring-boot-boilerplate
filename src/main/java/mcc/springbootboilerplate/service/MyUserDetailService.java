package mcc.springbootboilerplate.service;

import mcc.springbootboilerplate.entity.MyUser;
import mcc.springbootboilerplate.repository.MyUserRepository;
import mcc.springbootboilerplate.security.MyUserDetails;
import org.apache.commons.lang3.time.CalendarUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    Logger logger = LoggerFactory.getLogger(MyUserDetailService.class);

    @Value("${user.max-failed-attempts:3}")
    private int maxFailedAttempts;

    @Value("${user.password-expiry-date-count:90}")
    private int passwordExpiryDateCount;

    @Autowired
    MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("loadUserByUsername" + userName);
        Optional<MyUser> byUsername = myUserRepository.findByUsername(userName);

        if (byUsername.isEmpty()){
            throw new UsernameNotFoundException(userName);
        }
        MyUser myUser = byUsername.get();
        logger.info(myUser.getEncPassword() + ": " + myUser.getUsername());
        //MyUserDetails myUserDetails = new MyUserDetails(userName, byUsername.get().getEncPassword());
        Calendar expiryDate = Calendar.getInstance();
        expiryDate.add(Calendar.DATE, passwordExpiryDateCount);
        UserDetails user = User.withUsername(myUser.getUsername())
                .password(myUser.getEncPassword())
                .roles("USER")
                .credentialsExpired(myUser.getLastPasswordDate().after(expiryDate.getTime()))
                .accountLocked(myUser.getFailedAttempts() >= maxFailedAttempts)
                .disabled(myUser.getIsDisabled() == 1)
                .build();
        return user;
    }




}
