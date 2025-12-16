package org.example.presentation;

import org.example.entity.Room;
import org.example.enums.RoomType;
import org.example.services.RoomService;

import java.util.Scanner;

public class RoomPresentation {

    private RoomService roomService;
    private Scanner scanner;

    public RoomPresentation(RoomService roomService) {
        this.roomService = roomService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("^_^ ROOM MENU ^_^");
            System.out.println("1. Create/Update Room");
            System.out.println("2. Get Room By Number");
            System.out.println("3. Print All Rooms");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        createOrUpdateRoom();
                        break;
                    case 2:
                        getRoomByNumber();
                        break;
                    case 3:
                        printAllRooms();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void createOrUpdateRoom() {
        System.out.print("Enter Room Number: ");
        int roomNumber = scanner.nextInt();
        System.out.print("Enter Room Type (STANDARD, JUNIOR, MASTER): ");
        String typeStr = scanner.next().toUpperCase();
        RoomType roomType = RoomType.valueOf(typeStr);
        System.out.print("Enter Price Per Night: ");
        int price = scanner.nextInt();
        Room room = roomService.createRoom(roomNumber, roomType, price);
        System.out.println("Room created/updated: " + room);
    }

    private void getRoomByNumber() {
        System.out.print("Enter Room Number: ");
        int roomNumber = scanner.nextInt();
        Room room = roomService.getRoomByNumber(roomNumber);
        if (room != null) {
            System.out.println("Found room: " + room);
        } else {
            System.out.println("Room not found.");
        }
    }

    private void printAllRooms() {
        System.out.println("\n+++ ALL ROOMS +++");
        System.out.println("RoomNumber | RoomType | PricePerNight");
        System.out.println("------------------------------------------");
        roomService.printAllRooms();
        System.out.println("++++\n");
    }
}
