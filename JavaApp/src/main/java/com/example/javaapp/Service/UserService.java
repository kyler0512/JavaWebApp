package com.example.javaapp.Service;

import com.example.javaapp.Model.User;
import com.example.javaapp.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<User> findByUser(String username) {
        return userRepository.findUserByUsername(username);
    }
    public boolean checkExist(String username) {
        return findByUser(username).isPresent();
    }
    public List<User> getUser() {
        return userRepository.findAll();
    }
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
