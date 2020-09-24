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

/**
 * Controller Class for Entry
 */
@RestController
@RequestMapping("/entries")
public class EntryController {
    private EntryService entryService;
    private UserController applicationUserController;
    private WebConfiguration webConfiguration;

    /**
     * Constructor for Entry Controller, receives injected dependencies
     * @param entryService
     * @param categoryController
     * @param applicationUserController
     * @param webConfiguration
     */
    public EntryController(EntryService entryService, CategoryController categoryController, UserController applicationUserController, WebConfiguration webConfiguration) {
        this.entryService = entryService;
        this.applicationUserController = applicationUserController;
        this.webConfiguration = webConfiguration;
    }

    /**
     * Get Endpoint for Entry
     * @param token JWT Token
     * @return All selected Entries
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Entry> getAllEntries(@RequestHeader("Authorization") String token) {
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);

        return entryService.getAllEntries(applicationUser);
    }

    /**
     * Get Endpoint for Entry
     * @param id ID to select Entry
     * @param token JWT Token
     * @return Selected Entry
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Entry getSingleEntry(@PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);

        return entryService.getSingleEntry(id, applicationUser);
    }

    /**
     * Post Endpoint for Entry
     * @param entry Data coming from Client
     * @param token JWT Token
     * @return newly created Entry
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entry createEntry(@Valid @RequestBody Entry entry, @RequestHeader("Authorization") String token) {
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        entry.setUser(applicationUser);
        return entryService.createEntry(entry);
    }

    /**
     * Put Endpoint for Entry
     * @param entry Changed data from Client
     * @param id ID of the changed Entry
     * @param token JWT Token
     * @return changed Entry
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Entry editEntry(@RequestBody Entry entry, @PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        return entryService.editEntry(entry, id, applicationUser);
    }

    /**
     * Delete Endpoint for Entry
     * @param id ID of the deleted Entry
     * @param token JWT Token
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntry(@PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        entryService.deleteEntry(id, applicationUser);
    }
}
