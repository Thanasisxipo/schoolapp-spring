package gr.aueb.cf.schoolapppro.repository;

import gr.aueb.cf.schoolapppro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameStartingWith(String username);
    User findUserById(Long id);
}
