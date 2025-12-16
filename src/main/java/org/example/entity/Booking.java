package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private int id;
    private User user;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int totalPrice;

}
