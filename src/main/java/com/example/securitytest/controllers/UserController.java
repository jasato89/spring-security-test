package com.example.securitytest.controllers;

import com.example.securitytest.dto.UserDTO;
import com.example.securitytest.models.User;
import com.example.securitytest.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    public User createNewUser(@RequestBody UserDTO userDTO){
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username already exists");
        }
        System.out.println(userDTO.getUsername());
        System.out.println(userDTO.getPassword());
        User user = userRepository.save(new User(userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword())));
        user.setRole("USER");
        return userRepository.save(user);


    }
}
