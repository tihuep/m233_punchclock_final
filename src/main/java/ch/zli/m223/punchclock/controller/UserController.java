package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.ApplicationUser;
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

    public UserController(ApplicationUserRepository applicationUserRepository,
                          ApplicationUserService applicationUserService) {
        this.applicationUserRepository = applicationUserRepository;
        this.applicationUserService = applicationUserService;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationUser signUp(@RequestBody ApplicationUser user) {
        return applicationUserService.createUser(user);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ApplicationUser> getAllUsers(){
        return applicationUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationUser getSingleUser(@PathVariable Long id){
        return applicationUserService.getSingleUser(id);
    }

    @GetMapping("?username={username}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationUser getUserByUsername(@PathVariable String username){
        return applicationUserService.getUserByUsername(username);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApplicationUser editUser(@RequestBody ApplicationUser applicationUser, @PathVariable Long id){
        return applicationUserService.editUser(applicationUser, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id){
        applicationUserService.deleteUser(id);
    }
}
