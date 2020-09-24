package ch.zli.m223.punchclock.repository;

import ch.zli.m223.punchclock.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    /*@Query("SELECT * FROM USER_ROLE WHERE ROLE_NAME = ?1")
    Collection<UserRole> getUserRoleByName(String rolename);*/
}
