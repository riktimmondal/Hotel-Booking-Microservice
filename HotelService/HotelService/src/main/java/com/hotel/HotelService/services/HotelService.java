package com.hotel.HotelService.services;

import java.util.List;

import com.hotel.HotelService.entities.Hotel;

public interface HotelService {

    Hotel create(Hotel hotel);

    List<Hotel> getAll();

    Hotel get(String hotelId);
    
}