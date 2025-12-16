package org.example.presentation;

import org.example.entity.Booking;
import org.example.services.BookingService;
import java.time.LocalDate;
import java.util.Scanner;

public class BookingPresentation {

    private BookingService bookingService;
    private Scanner scanner;

    public BookingPresentation(BookingService bookingService) {
        this.bookingService = bookingService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("^_^ BOOKING MENU ^_^");
            System.out.println("1. Book Room");
            System.out.println("2. Print All Bookings");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        bookRoom();
                        break;
                    case 2:
                        printAllBookings();
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

    private void bookRoom() throws Exception {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter Room Number: ");
        int roomNumber = scanner.nextInt();
        System.out.print("Enter Check-in Date (yyyy-MM-dd): ");
        LocalDate checkIn = LocalDate.parse(scanner.next());
        System.out.print("Enter Check-out Date (yyyy-MM-dd): ");
        LocalDate checkOut = LocalDate.parse(scanner.next());

        Booking booking = bookingService.bookRoom(userId, roomNumber, checkIn, checkOut);
        System.out.println("Booking successful: " + booking);
    }

    private void printAllBookings() {
        System.out.println("\n +++ ALL BOOKINGS +++ ");
        System.out.println("BookingID | UserID | UserBalance | RoomNumber | RoomType | RoomPrice | CheckIn | CheckOut | TotalPrice");
        System.out.println("----------------------------------------------------------------------------------------------------");
        bookingService.printAllBookings();
        System.out.println("++++\n");
    }
}
