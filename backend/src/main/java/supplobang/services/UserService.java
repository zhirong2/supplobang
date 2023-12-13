package supplobang.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import supplobang.entities.User;

public interface UserService {

    public UserDetailsService userDetailsService();

}
