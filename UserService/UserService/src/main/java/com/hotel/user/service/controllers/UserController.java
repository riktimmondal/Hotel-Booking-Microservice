package com.hotel.user.service.controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.user.service.entities.User;
import com.hotel.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
    int retryCount = 1;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{userId}")
    //@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    //@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallbackForRateLimiter")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId)
    {
        logger.info("Retry count: " + retryCount++);
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //creating fall back method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex)
    {
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy User")
                .about("This user is created as a fallback due to an error")
                .userId("12423535")
                .build();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //creating fall back method for circuit breaker
    public ResponseEntity<User> ratingHotelFallbackForRateLimiter(String userId, Exception ex)
    {
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy User")
                .about("This user is created as a fallback due to an error")
                .userId("12423535")
                .build();

        return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUser()
    {
        List<User> user = userService.getAllUser();
        return ResponseEntity.ok(user);
    }
}
