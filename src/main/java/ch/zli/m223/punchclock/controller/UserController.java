package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.config.WebConfiguration;
import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.error_handling.ForbiddenException;
import ch.zli.m223.punchclock.repository.ApplicationUserRepository;
import ch.zli.m223.punchclock.service.ApplicationUserService;
import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private ApplicationUserRepository applicationUserRepository;
    private ApplicationUserService applicationUserService;
    private WebConfiguration webConfiguration;

    public UserController(ApplicationUserRepository applicationUserRepository,
                          ApplicationUserService applicationUserService,
                          WebConfiguration webConfiguration) {
        this.applicationUserRepository = applicationUserRepository;
        this.applicationUserService = applicationUserService;
        this.webConfiguration = webConfiguration;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationUser signUp(@RequestBody ApplicationUser user) {
        return applicationUserService.createUser(user);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationUser> getAllUsers(@RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        return applicationUserService.getAllUsers(applicationUser);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationUser getSingleUser(@PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        return applicationUserService.getSingleUser(id, applicationUser);
    }

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationUser getUserByUsername(@PathVariable String username, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        return applicationUserService.getUserByUsername(username, applicationUser);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationUser editUser(@RequestBody ApplicationUser applicationUser, @PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser1 = webConfiguration.getUserFromToken(token);
        return applicationUserService.editUser(applicationUser, id, applicationUser1);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        applicationUserService.deleteUser(id, applicationUser);
    }
}
