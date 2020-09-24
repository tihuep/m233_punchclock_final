package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.domain.UserRole;
import ch.zli.m223.punchclock.error_handling.ForbiddenException;
import ch.zli.m223.punchclock.repository.ApplicationUserRepository;
import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class ApplicationUserService implements UserDetailsService {
    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRoleService userRoleService;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                  UserRoleService userRoleService) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRoleService = userRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }

    public ApplicationUser createUser(ApplicationUser applicationUser){
        if (userRoleService.getAllUserRoles().size() == 0){
            UserRole userRole = new UserRole();
            userRole.setRoleName("Administrator");
            userRole.setAdmin(true);
            userRole.setId(Long.valueOf(1));
            userRoleService.createFirstUserRole(userRole);
        }

        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        return applicationUserRepository.save(applicationUser);
    }

    public List<ApplicationUser> getAllUsers(ApplicationUser user){
        if (!user.getRole().getAdmin())
            throw new ForbiddenException("Only admins allowed");
        return applicationUserRepository.findAll();
    }

    public ApplicationUser getSingleUser(Long id, ApplicationUser user){
        if (user.getId() != id && !user.getRole().getAdmin())
            throw new ForbiddenException("Foreign users not allowed");
        return applicationUserRepository.findById(id).get();
    }

    public ApplicationUser getUserByUsername(String username){
        return applicationUserRepository.findByUsername(username);
    }

    public ApplicationUser getUserByUsername(String username, ApplicationUser user){
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (!user.equals(applicationUser) && !user.getRole().getAdmin())
            throw new ForbiddenException("Cannot access foreign user");
        return applicationUser;
    }

    public ApplicationUser editUser(ApplicationUser editedUser, Long id, ApplicationUser user){
        this.getSingleUser(id, user);
        editedUser.setId(id);
        editedUser.setUsername(user.getUsername());
        return applicationUserRepository.save(editedUser);
    }

    public void deleteUser(Long id, ApplicationUser user){
        if (id != user.getId() && !user.getRole().getAdmin())
            throw new ForbiddenException("Cannot delete foreign users");
        applicationUserRepository.deleteById(id);
    }
}