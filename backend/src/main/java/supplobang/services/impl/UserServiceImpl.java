package supplobang.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import supplobang.entities.User;
import supplobang.repository.UserRepository;
import supplobang.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    // private BCryptPasswordEncoder encoder;

    // @Autowired
    // public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder){
    //     this.userRepository = userRepository;
    //     this.encoder = encoder;
    // }
        
    // public UserServiceImpl(UserRepository userRepository){
    //     this.userRepository = userRepository;
    // }

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username){
                return userRepository.findByEmail(username)
                       .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
            }  
        };
    }

    // @Override
    // public User createUser(User newUser){
    //     // String encodedPassword = encoder.encode(newUser.getPassword());
    //     String encodedPassword = newUser.getPassword();
    //     newUser.setPassword(encodedPassword);
    //     return userRepository.save(newUser);
    // }

    // @Override
    // public User updateUser(User updatedUser){
    //     String email = updatedUser.getEmail();
   
    //     return userRepository.findByEmail(email).map(existingUser -> {
    //         existingUser.setUsername(updatedUser.getUsername());
    //         existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
    //         existingUser.setStreetName(updatedUser.getStreetName());
    //         existingUser.setBlockNumber(updatedUser.getBlockNumber());
    //         existingUser.setUnitNumber(updatedUser.getUnitNumber());
    //         existingUser.setPostalCode(updatedUser.getPostalCode());
    //         return userRepository.save(existingUser);
    //     }).orElse(null);
    // }

    // @Override
    // public void deleteUser(User user) {
    //     long userId = user.getId(); 

    //     if (userRepository.existsById(userId)) {
    //         userRepository.deleteById(userId);
    //     }
    // }
    
    // @Override
    // public User authenticate(String email, String password){
        
    //     if(userRepository.findByEmail(email).isPresent()){
    //         User user = userRepository.findByEmail(email).get();
    //         if(encoder.matches(password, user.getPassword())){
    //             return user;
    //         }
    //     }

    //     return null;
    // }

}
