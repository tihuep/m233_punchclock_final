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

/**
 * Controller class for User
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private ApplicationUserRepository applicationUserRepository;
    private ApplicationUserService applicationUserService;
    private WebConfiguration webConfiguration;

    /**
     * Constructor for User Controller, received injected dependencies
     * @param applicationUserRepository
     * @param applicationUserService
     * @param webConfiguration
     */
    public UserController(ApplicationUserRepository applicationUserRepository,
                          ApplicationUserService applicationUserService,
                          WebConfiguration webConfiguration) {
        this.applicationUserRepository = applicationUserRepository;
        this.applicationUserService = applicationUserService;
        this.webConfiguration = webConfiguration;
    }

    /**
     * Post Endpoint for User
     * @param user Data coming from Client
     * @return newly created User
     */
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationUser signUp(@RequestBody ApplicationUser user) {
        return applicationUserService.createUser(user);
    }

    /**
     * Get Endpoint for User
     * @param token JWT Token
     * @return All selected Users
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationUser> getAllUsers(@RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        return applicationUserService.getAllUsers(applicationUser);
    }

    /**
     * Get Endpoint for User
     * @param id ID of the selected User
     * @param token JWT Token
     * @return Selected User
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationUser getSingleUser(@PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        return applicationUserService.getSingleUser(id, applicationUser);
    }

    /**
     * Get Endpoint for User, select with username instead of ID
     * @param username Username of the selected User
     * @param token JWT Token
     * @return selected User
     */
    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationUser getUserByUsername(@PathVariable String username, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        return applicationUserService.getUserByUsername(username, applicationUser);
    }

    /**
     * Put Endpoint for User
     * @param applicationUser Changed data from Client
     * @param id Id of changed User
     * @param token JWT Token
     * @return Changed User
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationUser editUser(@RequestBody ApplicationUser applicationUser, @PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser1 = webConfiguration.getUserFromToken(token);
        return applicationUserService.editUser(applicationUser, id, applicationUser1);
    }

    /**
     * Delete Endpoint for User
     * @param id ID of deleted User
     * @param token JWT Token
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        applicationUserService.deleteUser(id, applicationUser);
    }
}
