package com.hotel.HotelService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.HotelService.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String> {

    
}