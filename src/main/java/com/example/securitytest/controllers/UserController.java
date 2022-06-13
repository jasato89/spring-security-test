package com.example.securitytest.controllers;

import com.example.securitytest.models.User;
import com.example.securitytest.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @GetMapping("/say-hi")
    public String sayHi() {
        return "Hi";
    }

    @PostMapping("/create-user")
    public User createNewUser(@RequestBody User user, @AuthenticationPrincipal UserDetails userDetails){
        User userCreador = new User(userDetails.getUsername(), userDetails.getPassword());
        System.out.println(userCreador.getUsername());
        if (userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username already exists");
        }

        User user1 = userRepository.save(new User(user.getUsername(),
                passwordEncoder.encode(user.getPassword())));
        user1.setRole("USER");
        return userRepository.save(user1);
    }

    @GetMapping("/modify-password")
    public void modifyPassword(@RequestParam String newPassword, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }

    @GetMapping("/get-user-info")
    public User showToUsersOnly(@AuthenticationPrincipal UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername()).get();
    }
}
