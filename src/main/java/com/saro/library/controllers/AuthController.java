package com.saro.library.controllers;

import com.saro.library.dto.LoginUser;
import com.saro.library.dto.RegisterUser;
import com.saro.library.models.User;
import com.saro.library.repository.UserRepository;
import com.saro.library.services.UserService;
import com.saro.library.utils.JwtUtil;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Data
public class AuthController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUser registerUser){
        String email = registerUser.getEmail();
        String password = passwordEncoder.encode(registerUser.getPassword());

        if(email == null ||email.isEmpty() || password == null || password.isEmpty()){
            Map<String,Object> response = new HashMap<>();
            response.put("status", false);
            response.put("message", "Email or password is required");
            return ResponseEntity.badRequest().body(response);
        }

        if(userRepository.findByEmail(email).isPresent()){
            Map<String,Object> response = new HashMap<>();
            response.put("status", false);
            response.put("message", "Email Already registered");
            return ResponseEntity.badRequest().body(response);
        }

        User user = User.builder().email(email).password(password).role("USER").build();
        userService.createUser(user);

        Map<String,Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "User registered Successfully");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginUser loginUser){
        String email = loginUser.getEmail();
        String password = loginUser.getPassword();

        var optionalUser = userRepository.findByEmail(email);


        if(optionalUser.isEmpty()){
            Map<String,Object> response = new HashMap<>();
            response.put("status", false);
            response.put("message", "No user found with this email");
            return ResponseEntity.status(404).body(response);
        }

        User user = optionalUser.get();
        if(!passwordEncoder.matches(password,user.getPassword())){
            Map<String,Object> response = new HashMap<>();
            response.put("status", false);
            response.put("message", "Incorrect password");
            return ResponseEntity.status(403).body(response);
        }

        String token = jwtUtil.generateToken(email);

        Map<String,Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Login Successfull");
        response.put("token", token);
        return ResponseEntity.status(200).body(response);

    }
}
