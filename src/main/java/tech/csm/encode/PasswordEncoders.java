package tech.csm.encode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoders {

    @Bean
    public PasswordEncoder passwordEncoder() {    // <-- different name from class
        return new BCryptPasswordEncoder();
    }
}