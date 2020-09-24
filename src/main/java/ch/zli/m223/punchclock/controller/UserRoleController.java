package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.config.WebConfiguration;
import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.domain.UserRole;
import ch.zli.m223.punchclock.service.UserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for UserRole
 */
@RestController
@RequestMapping("/roles")
public class UserRoleController {
    private UserRoleService userRoleService;
    private WebConfiguration webConfiguration;

    /**
     * Constructor for UserRole, receives injected dependencies
     * @param userRoleService
     * @param webConfiguration
     */
    public UserRoleController(UserRoleService userRoleService, WebConfiguration webConfiguration){
        this.userRoleService = userRoleService;
        this.webConfiguration = webConfiguration;
    }

    /**
     * Post Endpoint for UserRole
     * @param userRole Data coming from Client
     * @param token JWT Token
     * @return newly created UserRole
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRole createUserRole(@Valid @RequestBody UserRole userRole, @RequestHeader("Authorization") String token) {
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        return userRoleService.createUserRole(userRole, applicationUser);
    }

    /**
     * Get Endpoint for UserRole
     * @return All UserRoles
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserRole> getAllUserRoles(){
        return userRoleService.getAllUserRoles();
    }

    /**
     * Get Endpoint for UserRole
     * @param id ID of selected UserRole
     * @return selected UserRole
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserRole getSingleUserRole(@PathVariable Long id){
        return userRoleService.getSingleUserRole(id);
    }
/*
    @GetMapping("/rolename/{rolename}")
    @ResponseStatus(HttpStatus.OK)
    public UserRole getUserRoleByName(@PathVariable String rolename){
        return userRoleService.getUserRoleByName(rolename);
    }
*/

    /**
     * Delete Endpoint for UserRole
     * @param id ID of deleted UserRole
     * @param token JWT Token
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserRole(@PathVariable Long id, @RequestHeader("Authorization") String token){
        ApplicationUser applicationUser = webConfiguration.getUserFromToken(token);
        userRoleService.deleteUserRole(id, applicationUser);
    }
}
