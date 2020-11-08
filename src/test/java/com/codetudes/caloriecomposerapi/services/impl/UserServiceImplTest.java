package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.db.domain.User;
import com.codetudes.caloriecomposerapi.db.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    /**
     * should return the current user
     */

    @Test
    void getCurrentUser_returnsHardCodedUserForNow() {
        // arrange
        User expected = new User();
        when(userRepository.findAll()).thenReturn(Collections.singletonList(expected));

        // act
        User actual = userService.getCurrentUser();

        // assert
        assertSame(expected, actual, "should return expected user");
    }
}
