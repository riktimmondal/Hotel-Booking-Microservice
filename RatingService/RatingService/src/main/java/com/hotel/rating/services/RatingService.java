package com.hotel.rating.services;

import java.util.List;

import com.hotel.rating.entities.Rating;

public interface RatingService {

    Rating create(Rating rating);

    List<Rating> getAll();

    List<Rating> getRatingByUserId(String userId);

    List<Rating> getRatingByHotelId(String hotelId);

    

    
}