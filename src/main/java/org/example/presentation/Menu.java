package org.example.presentation;

import org.example.services.BookingService;
import org.example.services.RoomService;
import org.example.services.UserService;

import java.util.Scanner;

public class Menu {

    private UserPresentation userPresentation;
    private RoomPresentation roomPresentation;
    private BookingPresentation bookingPresentation;
    private Scanner scanner;

    public Menu(UserService userService, RoomService roomService, BookingService bookingService) {
        this.userPresentation = new UserPresentation(userService);
        this.roomPresentation = new RoomPresentation(roomService);
        this.bookingPresentation = new BookingPresentation(bookingService);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\n+++ MAIN MENU +++");
            System.out.println("1. User Menu");
            System.out.println("2. Room Menu");
            System.out.println("3. Booking Menu");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    userPresentation.start();
                    break;
                case 2:
                    roomPresentation.start();
                    break;
                case 3:
                    bookingPresentation.start();
                    break;
                case 0:
                    System.out.println("Exiting program");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
