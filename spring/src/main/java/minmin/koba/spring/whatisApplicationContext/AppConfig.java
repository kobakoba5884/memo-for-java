package minmin.koba.spring.whatisApplicationContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import minmin.koba.spring.whatisDI.repositories.JdbcUserRepository;
import minmin.koba.spring.whatisDI.repositories.UserRepository;
import minmin.koba.spring.whatisDI.services.UserService;
import minmin.koba.spring.whatisDI.services.UserServiceImpl;

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
