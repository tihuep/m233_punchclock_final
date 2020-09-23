package ch.zli.m223.punchclock.controller;

import ch.zli.m223.punchclock.domain.Entry;
import ch.zli.m223.punchclock.domain.UserRole;
import ch.zli.m223.punchclock.service.UserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class UserRoleController {
    private UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService){
        this.userRoleService = userRoleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRole createUserRole(@Valid @RequestBody UserRole userRole) {
        return userRoleService.createUserRole(userRole);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserRole> getAllUserRoles(){
        return userRoleService.getAllUserRoles();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserRole getSingleUserRole(@PathVariable Long id){
        return userRoleService.getSingleUserRole(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserRole(@PathVariable Long id){
        userRoleService.deleteUserRole(id);
    }
}
