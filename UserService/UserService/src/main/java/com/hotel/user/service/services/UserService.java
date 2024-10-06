package com.hotel.user.service.services;

import java.util.List;

import com.hotel.user.service.entities.User;

public interface UserService {

    //User operations
    //create
    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get a user based on Id
    User getUser(String userId);

    //TODO: update
    //TODO: delete

    
}