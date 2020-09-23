package ch.zli.m223.punchclock.service;

import ch.zli.m223.punchclock.domain.UserRole;
import ch.zli.m223.punchclock.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    private UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository){
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole createUserRole(UserRole userRole){
        return userRoleRepository.saveAndFlush(userRole);
    }

    public List<UserRole> getAllUserRoles(){
        return userRoleRepository.findAll();
    }

    public UserRole getSingleUserRole(Long id){
        return userRoleRepository.findById(id).get();
    }

    public void deleteUserRole(Long id){
        userRoleRepository.deleteById(id);
    }
}
