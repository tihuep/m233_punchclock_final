package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.exception.ResourceNotFoundException;
import ch.zli.m223.punchclock.service.EntryService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.List;
import java.util.function.Predicate;

import static ch.zli.m223.punchclock.config.SecurityConstants.SECRET;
import static ch.zli.m223.punchclock.config.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/entries")
public class EntryController {
    private EntryService entryService;
    private UserController applicationUserController;

    public EntryController(EntryService entryService, CategoryController categoryController, UserController applicationUserController) {
        this.entryService = entryService;
        this.applicationUserController = applicationUserController;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Entry> getAllEntries(@RequestHeader("Authorization") String token) {
        String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();

        ApplicationUser applicationUser = applicationUserController.getUserByUsername(username);

        List<Entry> entries = entryService.findAll();

        entries.removeIf(new Predicate<Entry>() {
            @Override
            public boolean test(Entry entry) {
                return !entry.getUser().equals(applicationUser);
            }
        });

        return entries;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Entry getSingleEntry(@PathVariable Long id){
        return entryService.getSingleEntry(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entry createEntry(@Valid @RequestBody Entry entry, @RequestHeader("Authorization") String token) {
        String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();

        ApplicationUser applicationUser = applicationUserController.getUserByUsername(username);

        entry.setUser(applicationUser);

        return entryService.createEntry(entry);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Entry editEntry(@RequestBody Entry entry, @PathVariable Long id, @RequestHeader("Authorization") String token){
        String username = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();

        ApplicationUser applicationUser = applicationUserController.getUserByUsername(username);

        entry.setUser(applicationUser);

        return entryService.editEntry(entry, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEntry(@PathVariable Long id){
        entryService.deleteEntry(id);
    }
}
