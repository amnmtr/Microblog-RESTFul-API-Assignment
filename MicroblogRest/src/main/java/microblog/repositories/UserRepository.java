package microblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import microblog.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameIgnoreCase(String username);
}
