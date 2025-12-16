package org.example;

import org.example.presentation.Menu;
import org.example.services.BookingService;
import org.example.services.RoomService;
import org.example.services.UserService;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        RoomService roomService = new RoomService();
        BookingService bookingService = new BookingService(userService, roomService);

        Menu menu = new Menu(userService, roomService, bookingService);
        menu.start();
    }
}