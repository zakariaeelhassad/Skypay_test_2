package org.example.presentation;

import org.example.entity.User;
import org.example.services.UserService;

import java.util.Scanner;

public class UserPresentation {

    private UserService userService;
    private Scanner scanner;

    public UserPresentation(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("^_^ USER MENU ^_^");
            System.out.println("1. Create User");
            System.out.println("2. Get User By ID");
            System.out.println("3. Print All Users");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        createUser();
                        break;
                    case 2:
                        getUserById();
                        break;
                    case 3:
                        printAllUsers();
                        break;
                    case 0:
                        System.out.println("Exiting");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void createUser() {
        System.out.print("Enter User ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter Balance: ");
        int balance = scanner.nextInt();
        User user = userService.createUser(id, balance);
        System.out.println("User created/updated: " + user);
    }

    private void getUserById() {
        System.out.print("Enter User ID: ");
        int id = scanner.nextInt();
        User user = userService.getUserById(id);
        if (user != null) {
            System.out.println("Found user: " + user);
        } else {
            System.out.println("User not found.");
        }
    }

    private void printAllUsers() {
        System.out.println("\n+++ ALL USERS +++");
        System.out.println("ID | Balance");
        System.out.println("-------------------------------");

        userService.printAllUsers();

        System.out.println("++++\n");
    }
}
