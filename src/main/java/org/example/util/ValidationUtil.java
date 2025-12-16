package org.example.util;

import org.example.entity.Room;
import org.example.entity.User;
import org.example.exception.InvalidDateException;

import java.time.LocalDate;
import java.util.List;

public class ValidationUtil {

    public static void validateUserId(int userId , List<User> users) throws IllegalArgumentException {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive");
        }

        for (User user : users) {
            if (user.getId() == userId) {
                throw new IllegalArgumentException("Room number already exists: " + userId);
            }
        }
    }

    public static void validateBalance(int balance) throws IllegalArgumentException {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }

    public static void validateRoomNumber(int roomNumber, List<Room> rooms) throws IllegalArgumentException {
        if (roomNumber <= 0) {
            throw new IllegalArgumentException("Room number must be positive");
        }

        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                throw new IllegalArgumentException("Room number already exists: " + roomNumber);
            }
        }
    }

    public static void validatePricePerNight(int pricePerNight) throws IllegalArgumentException {
        if (pricePerNight <= 0) {
            throw new IllegalArgumentException("Price per night must be positive");
        }
    }

    public static void validateDates(LocalDate checkIn, LocalDate checkOut) throws InvalidDateException {
        if (checkIn == null || checkOut == null) {
            throw new InvalidDateException("Check-in and check-out dates cannot be null");
        }
        if (checkIn.isAfter(checkOut)) {
            throw new InvalidDateException("Check-in date must be before check-out date");
        }
        if (checkIn.isBefore(LocalDate.now())) {
            throw new InvalidDateException("Check-in date cannot be in the past");
        }
    }

    public static int calculateNights(LocalDate checkIn, LocalDate checkOut) {
        int nights = (int) (checkOut.toEpochDay() - checkIn.toEpochDay());
        return nights > 0 ? nights : 1;
    }
}