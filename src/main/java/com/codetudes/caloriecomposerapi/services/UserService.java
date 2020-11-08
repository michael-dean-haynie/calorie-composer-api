package com.codetudes.caloriecomposerapi.services;

import com.codetudes.caloriecomposerapi.db.domain.User;

public interface UserService {
    /**
     * Returns the user entity that made the current request
     * @return the user entity
     */
    User getCurrentUser();
}
