package mcc.springbootboilerplate.controller;

import mcc.springbootboilerplate.utils.RestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    @RequestMapping("/loginFailure")
    public ResponseEntity<RestBody> loginFailure(HttpServletRequest request){

        Exception attribute = (Exception) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (attribute == null ) {
            attribute = (Exception) request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
        logger.info(String.valueOf(attribute));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                RestBody.of(HttpStatus.UNAUTHORIZED, attribute.getMessage())
        );
    }

    @RequestMapping("/loginSuccess")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> loginSuccess(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(SecurityContextHolder.getContext().getAuthentication());
    }

    /*@PostMapping("/login")
    @ResponseBody
    public Principal login(Principal user) {
        return user;
    }

    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String pw) {
        return user;
    }

    @GetMapping("/logout")
    @ResponseBody
    public boolean logout(Principal user) {
        return true;
    }

     */
}
