package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.ApplicationUser;
import ch.zli.m223.punchclock.repository.ApplicationUserRepository;
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

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        return applicationUserRepository.save(applicationUser);
    }

    public List<ApplicationUser> getAllUsers(){
        return applicationUserRepository.findAll();
    }

    public ApplicationUser getSingleUser(Long id){
        return applicationUserRepository.findById(id).get();
    }

    public ApplicationUser getUserByUsername(String username){
        return applicationUserRepository.findByUsername(username);
    }

    public ApplicationUser editUser(ApplicationUser applicationUser, Long id){
        applicationUser.setId(id);
        return applicationUserRepository.save(applicationUser);
    }

    public void deleteUser(Long id){
        applicationUserRepository.deleteById(id);
    }
}