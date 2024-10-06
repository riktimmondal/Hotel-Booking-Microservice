package com.hotel.rating.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.rating.entities.Rating;
import com.hotel.rating.services.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getRatings(){
        return ResponseEntity.ok(ratingService.getAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(String userId){
        return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
    } 

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(String hotelId){
        return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelId));
    } 
    
}
