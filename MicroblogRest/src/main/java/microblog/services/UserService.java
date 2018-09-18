package microblog.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import microblog.models.User;


public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    boolean emailExists(String email);

    boolean usernameExists(String username);

    void register(User user);

    void authenticate(User user);

    boolean isAuthenticated();

    User currentUser();

}
