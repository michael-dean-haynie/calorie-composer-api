package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.db.domain.Unit;
import com.codetudes.caloriecomposerapi.db.domain.User;
import com.codetudes.caloriecomposerapi.db.repositories.UnitRepository;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import com.codetudes.caloriecomposerapi.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitRepository unitRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public Unit create(Unit unit) {
        // TODO: don't hard code this
        List<User> users = userRepository.findAll();
        if (users.size() > 0){
            unit.setUser(users.get(0));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Hard-coded user unable to be found while creating unit.");
        }
        return unitRepository.save(unit);
    }
}
