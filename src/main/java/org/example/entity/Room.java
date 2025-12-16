package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.RoomType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private int roomNumber ;
    private int pricePerNight ;
    private RoomType roomType ;
}
