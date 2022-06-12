package com.example.securitytest;

import com.example.securitytest.models.Role;
import com.example.securitytest.models.User;
import com.example.securitytest.repos.RoleRepository;
import com.example.securitytest.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class SecurityTestApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SecurityTestApplication.class, args);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
       User user = userRepository.save(new User("jasato", passwordEncoder.encode("1234")));
        System.out.println(user.getUsername());
       user.setRole("ADMIN");

        userRepository.save(user);
        System.out.println(user.getPassword());

    }
}
