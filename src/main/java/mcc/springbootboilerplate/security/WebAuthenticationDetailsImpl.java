package mcc.springbootboilerplate.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class WebAuthenticationDetailsImpl extends WebAuthenticationDetails {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAuthenticationDetailsImpl.class);

    private final String remoteAddress;

    public WebAuthenticationDetailsImpl(HttpServletRequest request) {
        super(request);
        //get remoteAddress if set in the header. otherwise, get it from the request
        String remoteAddress = request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR")  : request.getRemoteAddr();
        if(remoteAddress != null){
            String ips[]=remoteAddress.split(",");
            remoteAddress = StringUtils.trim(ips[0]);
        }
        this.remoteAddress = remoteAddress;
        try {
            LOGGER.info("X-Forwarded-For=" + request.getHeader("X-FORWARDED-FOR") + ", remote_ip=" + request.getRemoteAddr() +
                    ", username=" + StringUtils.join(request.getParameterValues("username")));
        } catch (Exception ex){
            LOGGER.error("Error", ex);
        }
    }

    @Override
    public String getRemoteAddress() {
        return remoteAddress;
    }
}
