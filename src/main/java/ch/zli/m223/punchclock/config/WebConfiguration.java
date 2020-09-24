package ch.zli.m223.punchclock.config;

import ch.zli.m223.punchclock.controller.UserController;
import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.service.ApplicationUserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import static ch.zli.m223.punchclock.config.SecurityConstants.SECRET;
import static ch.zli.m223.punchclock.config.SecurityConstants.TOKEN_PREFIX;

/**
 * Also see https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-cors
 */
@Configuration
public class WebConfiguration {

    private ApplicationUserService applicationUserService;

    public WebConfiguration(ApplicationUserService applicationUserService){
        this.applicationUserService = applicationUserService;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }

    public ApplicationUser getUserFromToken(String token){
        String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();

        ApplicationUser applicationUser = applicationUserService.getUserByUsername(username);

        return applicationUser;
    }
}
