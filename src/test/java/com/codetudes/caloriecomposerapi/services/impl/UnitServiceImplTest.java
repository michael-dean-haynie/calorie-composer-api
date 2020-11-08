package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.contracts.UnitDTO;
import com.codetudes.caloriecomposerapi.db.domain.Unit;
import com.codetudes.caloriecomposerapi.db.repositories.UnitRepository;
import com.codetudes.caloriecomposerapi.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class UnitServiceImplTest {
    @InjectMocks
    UnitServiceImpl unitService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    UserService userService;

    @Mock
    UnitRepository unitRepository;


    @Test
    void create__unitExistsForUser_400() {
        // prepare
        UnitDTO dto = new UnitDTO();
        Unit mappedUnit = new Unit();
        when(modelMapper.map(dto, Unit.class)).thenReturn(mappedUnit);

        when(unitRepository.findFirstByUserAndSingularAndPluralAndAbbreviation(any(), any(), any(), any()))
                .thenReturn(new Unit());

        // assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            // act
            unitService.create(dto);
        });
        assertEquals("response status should be 400 Bad Request", exception.getStatus(), HttpStatus.BAD_REQUEST);

    }
}