package org.example.services;

import org.example.entity.Room;
import org.example.enums.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceTest {

    private RoomService roomService;

    @BeforeEach
    void setUp() {
        roomService = new RoomService();
    }

    @Test
    void shouldCreateNewRoom() {
        Room room = roomService.createRoom(101, RoomType.STANDARD, 100);

        assertNotNull(room);
        assertEquals(101, room.getRoomNumber());
        assertEquals(100, room.getPricePerNight());
        assertEquals(RoomType.STANDARD, room.getRoomType());
    }

    @Test
    void shouldUpdateExistingRoom() {
        roomService.createRoom(101, RoomType.STANDARD, 100);
        Room updatedRoom = roomService.createRoom(101, RoomType.JUNIOR, 150);

        assertEquals(101, updatedRoom.getRoomNumber());
        assertEquals(150, updatedRoom.getPricePerNight());
        assertEquals(RoomType.JUNIOR, updatedRoom.getRoomType());
        assertEquals(1, roomService.getAllRooms().size());
    }

    @Test
    void shouldReturnRoomByNumber() {
        roomService.createRoom(102, RoomType.MASTER, 200);
        Room room = roomService.getRoomByNumber(102);

        assertNotNull(room);
        assertEquals(102, room.getRoomNumber());
        assertEquals(200, room.getPricePerNight());
        assertEquals(RoomType.MASTER, room.getRoomType());
    }

    @Test
    void shouldReturnNullIfRoomNotFound() {
        Room room = roomService.getRoomByNumber(999);
        assertNull(room);
    }

    @Test
    void shouldReturnAllRooms() {
        roomService.createRoom(101, RoomType.STANDARD, 100);
        roomService.createRoom(102, RoomType.JUNIOR, 200);

        List<Room> allRooms = roomService.getAllRooms();
        assertEquals(2, allRooms.size());
        assertEquals(101, allRooms.get(0).getRoomNumber());
        assertEquals(102, allRooms.get(1).getRoomNumber());
    }

    @Test
    void shouldThrowExceptionForInvalidRoomNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            roomService.createRoom(-1, RoomType.STANDARD, 100);
        });
        assertEquals("Room number must be positive", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidPrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            roomService.createRoom(103, RoomType.STANDARD, -50);
        });
        assertEquals("Price per night must be positive", exception.getMessage());
    }

}
