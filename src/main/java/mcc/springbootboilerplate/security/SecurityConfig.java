package mcc.springbootboilerplate.security;

import mcc.springbootboilerplate.service.MyUserDetailService;
import mcc.springbootboilerplate.service.MyUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.*;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAuthenticationDetailsImpl.class);

    @Autowired
    MyUserDetailService userDetailsService;

    @Autowired
    MyUserService myUserService;
    //@Autowired
    //@Qualifier("myAuthenticationProvider")
    MyAuthenticationProvider authenticationProvider;



    //@Bean
    /*public MyAuthenticationProvider myAuthenticationProvider() {
        LOGGER.debug("myAuthenticationProvider");
        MyAuthenticationProvider myAuthenticationProvider = new MyAuthenticationProvider();
        myAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        myAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return myAuthenticationProvider;
    }*/

    //@Bean
    public AuthenticationProvider authProvider() {
        MyAuthenticationProvider authProvider = new MyAuthenticationProvider();
        authProvider.setMyUserService(myUserService);
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> setting authProvider" + userDetailsService);
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
*/
        LOGGER.info("configure>>>>>>>>>>>>>>>>>>>>>>>>>");
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/register", "/health").permitAll()
                .antMatchers("/account/**").hasAuthority("USER")
                .and()
                //.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                //.and()
                //enforce HTTPS
                //.requiresChannel().anyRequest().requiresSecure()

                //Login configurations
                .formLogin()

                //.successForwardUrl("/loginSuccess")
                //.loginPage("/login")
                //.failureUrl("/login?error=true")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                //logout configurations
                .and()
                .logout().deleteCookies("")
                .logoutSuccessUrl("/login");
        http.cors();
        http.csrf().disable();
        //http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        //MySuccessHandler mySuccessHandler = new MySuccessHandler();
        //mySuccessHandler.setDefaultTargetUrl("/loginSuccess");
        // Since we use AJAX request for login, and use token for session
        // we forward to login success instead of redirect
        // if we redirect user to the loginSuccess page, the X-Auth-token will not bring to the redirect page and result in Access Denied
        ForwardAuthenticationSuccessHandler forwardAuthenticationSuccessHandler
                = new ForwardAuthenticationSuccessHandler("/loginSuccess");
        return forwardAuthenticationSuccessHandler;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new MyAccessDeniedHandler();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler failureHandler(){
        SimpleUrlAuthenticationFailureHandler failureHandler = new LoginAuthenticationFailureHandler();
        failureHandler.setDefaultFailureUrl("/loginFailure");
        failureHandler.setUseForward(true);
        return failureHandler;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:8080","http://127.0.0.1:3000","http://localhost:3000","http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}


