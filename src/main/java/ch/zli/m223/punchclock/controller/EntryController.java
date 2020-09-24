package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.config.WebConfiguration;
import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.error_handling.ForbiddenException;
import ch.zli.m223.punchclock.service.EntryService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Predicate;

import static ch.zli.m223.punchclock.config.SecurityConstants.SECRET;
import static ch.zli.m223.punchclock.config.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/entries")
public class EntryController {
    private EntryService entryService;
    private UserController applicationUserController;
    private WebConfiguration webConfiguration;

    public EntryController(EntryService entryService, CategoryController categoryController, UserController applicationUserController, WebConfiguration webConfiguration) {
        this.entryService = entryService;
        this.applicationUserController = applicationUserController;
        this.webConfiguration = webConfiguration;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Entry> getAllEntries(@RequestHeader("Authorization") String token) {
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);

        return entryService.getAllEntries(applicationUser);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Entry getSingleEntry(@PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);

        return entryService.getSingleEntry(id, applicationUser);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entry createEntry(@Valid @RequestBody Entry entry, @RequestHeader("Authorization") String token) {
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        entry.setUser(applicationUser);
        return entryService.createEntry(entry);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Entry editEntry(@RequestBody Entry entry, @PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        return entryService.editEntry(entry, id, applicationUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntry(@PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        entryService.deleteEntry(id, applicationUser);
    }
}
