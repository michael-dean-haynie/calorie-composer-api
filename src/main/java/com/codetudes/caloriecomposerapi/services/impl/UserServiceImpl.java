package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.db.domain.User;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.codetudes.caloriecomposerapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        // TODO: don't hard code this
        List<User> users = userRepository.findAll();
        if (users.size() > 0) {
            return users.get(0);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Hard-coded user unable to be found.");
        }
    }
}
