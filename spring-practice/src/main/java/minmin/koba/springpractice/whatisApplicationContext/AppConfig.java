package minmin.koba.springpractice.whatisApplicationContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import minmin.koba.springpractice.whatisDI.repositories.JdbcUserRepository;
import minmin.koba.springpractice.whatisDI.repositories.UserRepository;
import minmin.koba.springpractice.whatisDI.services.UserService;
import minmin.koba.springpractice.whatisDI.services.UserServiceImpl;

@Configuration
public class AppConfig {
    @Bean
    UserRepository userRepository(){
        return new JdbcUserRepository();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserService userService(){
        return new UserServiceImpl(userRepository(), passwordEncoder());
    }
    
}
