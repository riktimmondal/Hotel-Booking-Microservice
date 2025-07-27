package com.hotel.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hotel.user.service.entities.Rating;
import com.hotel.user.service.external.services.RatingService;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private RatingService ratingService;

	@Test
	void createRating(){
		Rating rating=Rating.builder().Rating(10).userId("").hotelId("").feedback("testing rating creation using feign library").build();

		Rating savedRating = ratingService.createRating(rating);

		System.out.println("rating created");
	}
}
