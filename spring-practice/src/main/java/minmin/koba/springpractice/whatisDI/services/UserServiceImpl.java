package minmin.koba.springpractice.whatisDI.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import minmin.koba.springpractice.whatisDI.errors.UserAlreadyRegisteredException;
import minmin.koba.springpractice.whatisDI.models.User;
import minmin.koba.springpractice.whatisDI.repositories.JdbcUserRepository;
import minmin.koba.springpractice.whatisDI.repositories.UserRepository;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // pattern 1
    public UserServiceImpl(){
        this.userRepository = new JdbcUserRepository();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // pattern 2
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(User user, String password) {
        if(this.userRepository.countByUsername(user.getUsername()) > 0){
            throw new UserAlreadyRegisteredException();
        }

        user.setPassword(this.passwordEncoder.encode(password));
        this.userRepository.save(user);

        System.out.println("successfully saved user!");
        
    }
    
}
