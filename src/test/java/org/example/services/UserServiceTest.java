package org.example.services;

import org.example.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void shouldCreateNewUser() {
        User user = userService.createUser(1, 500);

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals(500, user.getBalance());
    }

    @Test
    void shouldUpdateExistingUserBalance() {
        userService.createUser(1, 500);
        User updatedUser = userService.createUser(1, 1000);

        assertEquals(1, updatedUser.getId());
        assertEquals(1000, updatedUser.getBalance());
        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    void shouldReturnUserById() {
        userService.createUser(2, 200);
        User user = userService.getUserById(2);

        assertNotNull(user);
        assertEquals(2, user.getId());
        assertEquals(200, user.getBalance());
    }

    @Test
    void shouldReturnNullIfUserNotFound() {
        User user = userService.getUserById(99);
        assertNull(user);
    }

    @Test
    void shouldReturnAllUsers() {
        userService.createUser(1, 100);
        userService.createUser(2, 200);

        List<User> allUsers = userService.getAllUsers();
        assertEquals(2, allUsers.size());
        assertEquals(1, allUsers.get(0).getId());
        assertEquals(2, allUsers.get(1).getId());
    }

    @Test
    void shouldThrowExceptionForInvalidUserId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(-1, 100);
        });
        assertEquals("User ID must be positive", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidBalance() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(1, -100);
        });
        assertEquals("Balance cannot be negative", exception.getMessage());
    }
}
