package com.bookbuddy.bookbuddy.ServiceClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbuddy.bookbuddy.Entities.UserCheckout;
import com.bookbuddy.bookbuddy.Repository.UserCheckoutRepository;

@Service
public class  UserCheckoutService {

    @Autowired
    UserCheckoutRepository userCheckoutRepository;
    
    public UserCheckout createUserCheckout(UserCheckout userCheckout) {
        return userCheckoutRepository.save(userCheckout);
    }
}
