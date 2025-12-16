package org.example.services;

import org.example.entity.Booking;
import org.example.entity.Room;
import org.example.entity.User;
import org.example.exception.*;
import org.example.util.ValidationUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private List<Booking> bookings = new ArrayList<>();
    private int bookingIdCounter = 1;
    private UserService userService;
    private RoomService roomService;

    public BookingService(UserService userService, RoomService roomService) {
        this.userService = userService;
        this.roomService = roomService;
    }

    public Booking bookRoom(int userId, int roomNumber, LocalDate checkIn, LocalDate checkOut)
            throws InvalidDateException, UserNotFoundException, RoomNotFoundException,
            InsufficientBalanceException, RoomAlreadyBookedException {

        ValidationUtil.validateDates(checkIn, checkOut);

        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        Room room = roomService.getRoomByNumber(roomNumber);
        if (room == null) {
            throw new RoomNotFoundException("Room with number " + roomNumber + " not found");
        }

        int nights = ValidationUtil.calculateNights(checkIn, checkOut);
        int totalPrice = nights * room.getPricePerNight();

        if (user.getBalance() < totalPrice) {
            throw new InsufficientBalanceException(
                    "Insufficient balance. Required: " + totalPrice + ", Available: " + user.getBalance()
            );
        }

        for (Booking b : bookings) {
            if (b.getRoom().getRoomNumber() == roomNumber &&
                    datesOverlap(checkIn, checkOut, b.getCheckIn(), b.getCheckOut())) {
                throw new RoomAlreadyBookedException(
                        "Room " + roomNumber + " is already booked for this period"
                );
            }
        }

        Room roomSnapshot = new Room(
                room.getRoomNumber(),
                room.getPricePerNight(),
                room.getRoomType()
        );

        User userSnapshot = new User(user.getId(), user.getBalance());

        user.setBalance(user.getBalance() - totalPrice);

        Booking booking = new Booking(
                bookingIdCounter++,
                userSnapshot,
                roomSnapshot,
                checkIn,
                checkOut,
                totalPrice
        );
        bookings.add(booking);

        return booking;
    }

    private boolean datesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }

    public void printAllBookings() {
        for (int i = bookings.size() - 1; i >= 0; i--) {
            Booking b = bookings.get(i);
            System.out.println(b.getId() + " | " +
                    b.getUser().getId() + " | " +
                    b.getUser().getBalance() + " | " +
                    b.getRoom().getRoomNumber() + " | " +
                    b.getRoom().getRoomType() + " | " +
                    b.getRoom().getPricePerNight() + " | " +
                    b.getCheckIn() + " | " +
                    b.getCheckOut() + " | " +
                    b.getTotalPrice());
        }
    }

    public List<Booking> getAllBookings() {
        return bookings;
    }
}