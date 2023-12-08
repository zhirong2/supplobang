package supplobang.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import supplobang.entities.User;

public interface UserService {
    // public User createUser(User newUser);
    // public User updateUser(User updatedUser);
    // public void deleteUser(User user);
    // public User authenticate(String email, String password);
    public UserDetailsService userDetailsService();

}
