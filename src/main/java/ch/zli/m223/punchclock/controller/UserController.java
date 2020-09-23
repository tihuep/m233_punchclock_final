package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.repository.ApplicationUserRepository;
import ch.zli.m223.punchclock.service.ApplicationUserService;
import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserRepository applicationUserRepository,
                          ApplicationUserService applicationUserService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.applicationUserService = applicationUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }

    @GetMapping()
    public List<ApplicationUser> getAllUsers(){
        return applicationUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ApplicationUser getSingleUser(@PathVariable Long id){
        return applicationUserService.getSingleUser(id);
    }

    @PutMapping
    public ApplicationUser editUser(@RequestBody ApplicationUser applicationUser, @PathVariable Long id){
        return applicationUserService.editUser(applicationUser, id);
    }

    @DeleteMapping
    public void deleteUser(@PathVariable Long id){
        applicationUserService.deleteUser(id);
    }
}
