package com.esi.authservice.users.controller;

import com.esi.authservice.jwt.JwtService;
import com.esi.authservice.users.dto.UserDto;
import com.esi.authservice.users.model.User;
import com.esi.authservice.users.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@Tag(name = "Authorization", description = "CRUD Operations for auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String logInAndGetToken(@RequestBody UserDto userDto) {

        if (userDto.getName() == null || userDto.getPassword() == null) {
            throw new UsernameNotFoundException("UserName or Password is Empty");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getName(), userDto.getPassword()));
            if (authentication.isAuthenticated()) {
                log.info("jwtService.generateToken(authRequest.getName())  {} ", jwtService.generateToken(userDto.getName()).toString());
                return jwtService.generateToken(userDto.getName());
            } else {
                throw new UsernameNotFoundException("The user cannot be authinticated!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/authenticate")
    public Boolean authenticate(@RequestHeader("Authorization") String header) {
        String token = header.replace("Bearer ", "");
        log.info(" authenticate - token {} ", token);
        return jwtService.validateToken(token);
    }

    @PostMapping("/signup")
    public String signupUser(@RequestBody User user) {
        try {
            userService.addUser(user);
            String jwtToken = jwtService.generateToken(user.getName());
            return jwtToken;
        } catch (Exception e) {
            return "User already exists";
        }
    }
}
