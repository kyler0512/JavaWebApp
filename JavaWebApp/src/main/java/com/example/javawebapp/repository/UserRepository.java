package com.example.javawebapp.repository;

import com.example.javawebapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private Map<Integer, User> userList = new HashMap<>();
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public Map<Integer, User> getUserList() {
        return userList;
    }


//    private void initUser() {
//        User u1 = new User(1, "admin", "admin");
//        User u2 = new User(2, "uy", "pass1");
//        userList.put(u1.getId(), u1);
//        userList.put(u2.getId(), u2);
//    }

    public User getUser(Integer id) {
        return userList.get(id);
    }
    public User addUser(User user) {
        userList.put(user.getId(), user);
        return user;
    }
    public User updateUser(User user) {
        userList.put(user.getId(), user);
        return user;
    }
    public void deleteUser(Integer id) {
        userList.remove(id);
    }
    public List<User> getAllUser() {
        Collection<User> collection = userList.values();
        List<User> list = new ArrayList<>();
        list.addAll(collection);
        return list;
    }
    public boolean checkExist(String username) {
        if (getAllUser().size() > 0) {
            for (User u : getAllUser()) {
                if (u.getUsername().equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }
    public User findUser(Integer id) {
        for (User u : getAllUser()) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }
}
