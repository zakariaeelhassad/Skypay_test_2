package org.example.services;

import org.example.entity.Booking;
    import org.example.entity.User;
import org.example.enums.RoomType;
import org.example.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    private UserService userService;
    private RoomService roomService;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        roomService = new RoomService();
        bookingService = new BookingService(userService, roomService);

        userService.createUser(1, 1000);
        roomService.createRoom(101, RoomType.STANDARD, 100);
    }

    @Test
    void shouldBookRoomSuccessfully() throws Exception {
        LocalDate checkIn = LocalDate.of(2025, 12, 20);
        LocalDate checkOut = LocalDate.of(2025, 12, 22);

        Booking booking = bookingService.bookRoom(1, 101, checkIn, checkOut);

        assertNotNull(booking);
        assertEquals(1, booking.getUser().getId());
        assertEquals(101, booking.getRoom().getRoomNumber());
        assertEquals(200, booking.getTotalPrice()); // 2 nuits x 100
        assertEquals(1, bookingService.getAllBookings().size());

        User user = userService.getUserById(1);
        assertEquals(800, user.getBalance());
    }

    @Test
    void shouldThrowUserNotFoundException() {
        LocalDate checkIn = LocalDate.of(2025, 12, 20);
        LocalDate checkOut = LocalDate.of(2025, 12, 22);

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            bookingService.bookRoom(999, 101, checkIn, checkOut);
        });

        assertEquals("User with ID 999 not found", exception.getMessage());
    }

    @Test
    void shouldThrowRoomNotFoundException() {
        LocalDate checkIn = LocalDate.of(2025, 12, 20);
        LocalDate checkOut = LocalDate.of(2025, 12, 22);

        Exception exception = assertThrows(RoomNotFoundException.class, () -> {
            bookingService.bookRoom(1, 999, checkIn, checkOut);
        });

        assertEquals("Room with number 999 not found", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidDateExceptionForInvalidDates() {
        LocalDate checkIn = LocalDate.of(2025, 12, 22);
        LocalDate checkOut = LocalDate.of(2025, 12, 20); // checkOut avant checkIn

        assertThrows(InvalidDateException.class, () -> {
            bookingService.bookRoom(1, 101, checkIn, checkOut);
        });
    }

    @Test
    void shouldThrowInsufficientBalanceException() throws Exception {
        userService.createUser(2, 50); // solde insuffisant
        LocalDate checkIn = LocalDate.of(2025, 12, 20);
        LocalDate checkOut = LocalDate.of(2025, 12, 22);

        Exception exception = assertThrows(InsufficientBalanceException.class, () -> {
            bookingService.bookRoom(2, 101, checkIn, checkOut);
        });

        assertEquals("Insufficient balance. Required: 200, Available: 50", exception.getMessage());
    }

    @Test
    void shouldThrowRoomAlreadyBookedException() throws Exception {
        LocalDate checkIn1 = LocalDate.of(2025, 12, 20);
        LocalDate checkOut1 = LocalDate.of(2025, 12, 22);

        bookingService.bookRoom(1, 101, checkIn1, checkOut1);

        LocalDate checkIn2 = LocalDate.of(2025, 12, 21);
        LocalDate checkOut2 = LocalDate.of(2025, 12, 23);

        Exception exception = assertThrows(RoomAlreadyBookedException.class, () -> {
            bookingService.bookRoom(1, 101, checkIn2, checkOut2);
        });

        assertEquals("Room 101 is already booked for this period", exception.getMessage());
    }

    @Test
    void shouldReturnAllBookings() throws Exception {
        LocalDate checkIn = LocalDate.of(2025, 12, 20);
        LocalDate checkOut = LocalDate.of(2025, 12, 22);

        bookingService.bookRoom(1, 101, checkIn, checkOut);

        List<Booking> bookings = bookingService.getAllBookings();
        assertEquals(1, bookings.size());
        assertEquals(101, bookings.get(0).getRoom().getRoomNumber());
    }
}
