package org.example.services;

import org.example.entity.Room;
import org.example.enums.RoomType;
import org.example.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private List<Room> rooms = new ArrayList<>();

    public Room createRoom(int roomNumber, RoomType roomType, int pricePerNight) {
        Room existingRoom = getRoomByNumber(roomNumber);

        if (existingRoom != null) {
            existingRoom.setRoomType(roomType);
            existingRoom.setPricePerNight(pricePerNight);
            return existingRoom;
        }

        ValidationUtil.validateRoomNumber(roomNumber, rooms);
        ValidationUtil.validatePricePerNight(pricePerNight);

        Room room = new Room(roomNumber, pricePerNight, roomType);
        rooms.add(room);
        return room;
    }


    public Room getRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public void printAllRooms() {
        for (int i = rooms.size() - 1; i >= 0; i--) {
            Room room = rooms.get(i);
            System.out.println(room.getRoomNumber() + " | " +
                    room.getRoomType() + " | " + room.getPricePerNight());
        }
    }

    public List<Room> getAllRooms() {
        return rooms;
    }
}