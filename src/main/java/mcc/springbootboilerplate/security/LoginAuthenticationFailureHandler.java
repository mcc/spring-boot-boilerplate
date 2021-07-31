package mcc.springbootboilerplate.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    Logger logger = LoggerFactory.getLogger(LoginAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("Failed authenticate " + request.getParameter("username"));
        super.onAuthenticationFailure(request, response, exception);
    }
}
