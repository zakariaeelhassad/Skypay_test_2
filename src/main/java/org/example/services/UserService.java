package org.example.services;

import org.example.entity.User;
import org.example.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users = new ArrayList<>();

    public User createUser(int id, int balance) {
        User existingUser = getUserById(id);

        if (existingUser != null) {
            existingUser.setBalance(balance);
            return existingUser;
        }

        ValidationUtil.validateUserId(id, users);
        ValidationUtil.validateBalance(balance);

        User user = new User(id, balance);
        users.add(user);
        return user;
    }


    public User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public void printAllUsers() {
        for (int i = users.size() - 1; i >= 0; i--) {
            User user = users.get(i);
            System.out.println(user.getId() + " | " + user.getBalance());
        }
    }

    public List<User> getAllUsers() {
        return users;
    }
}