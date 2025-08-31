package com.saro.library.services;

import com.saro.library.models.User;
import com.saro.library.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserService {
    private final UserRepository userRepository;

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found"));
    }

    public User createUser(User user){
        return userRepository.save(user);
    }
}
