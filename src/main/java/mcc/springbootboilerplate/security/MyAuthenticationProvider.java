package mcc.springbootboilerplate.security;

import mcc.springbootboilerplate.service.MyUserDetailService;
import mcc.springbootboilerplate.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    MyUserService myUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Authentication authenticate = super.authenticate(authentication);
            if (authenticate.isAuthenticated()) {
                myUserService.resetFailLoginAttempt(authentication.getName());
            }
            return authenticate;
        } catch (BadCredentialsException | UsernameNotFoundException ex){
            myUserService.increaseFailLoginAttempt(authentication.getName());
            throw ex;
        }
    }

    public void setMyUserService(MyUserService myUserService) {
        this.myUserService = myUserService;
    }


}
