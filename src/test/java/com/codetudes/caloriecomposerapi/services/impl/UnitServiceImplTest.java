package com.codetudes.caloriecomposerapi.services.impl;

import com.codetudes.caloriecomposerapi.contracts.UnitDTO;
import com.codetudes.caloriecomposerapi.db.domain.Unit;
import com.codetudes.caloriecomposerapi.db.repositories.UnitRepository;
import com.codetudes.caloriecomposerapi.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class UnitServiceImplTest {
    @InjectMocks
    UnitServiceImpl unitService;

    @Spy
    ModelMapper modelMapper = new ModelMapper();

    @Mock
    UserService userService;

    @Mock
    UnitRepository unitRepository;

    @Captor
    ArgumentCaptor<Unit> unitCaptor;


    @Test
    void create_notDraftAndMatchingUnitExistsForUser_400() {
        // arrange
        UnitDTO dto = new UnitDTO();
        dto.setIsDraft(false);

        when(unitRepository.findFirstByUserAndAbbreviationAndDraftOfIsNull(any(), any()))
                .thenReturn(new Unit());

        // assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            // act
            unitService.create(dto);
        });
        assertEquals("response status should be 400 Bad Request", exception.getStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void create_unitIsDraft_skipCheckForExistingMatches() {
        // arrange
        UnitDTO dto = new UnitDTO();
        dto.setIsDraft(true);

        when(unitRepository.save(any())).thenReturn(mock(Unit.class));

        // act
        unitService.create(dto);

        // assert
        verify(unitRepository, never()).findFirstByUserAndAbbreviationAndDraftOfIsNull(any(), any());
        verify(unitRepository).save(any());
    }

    @Test
    public void create_notADraftButHasDraft_relationshipSetOnDraft() {
        // arrange - unitDTO and it's draftDTO
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setIsDraft(false);

        UnitDTO draftUnitDTO = new UnitDTO();
        draftUnitDTO.setIsDraft(true);
        unitDTO.setDraft(draftUnitDTO);

        // arrange - stub repository responses
        when(unitRepository.save(any())).thenReturn(mock(Unit.class));

        // act
        unitService.create(unitDTO);

        // assert
        verify(unitRepository).save(unitCaptor.capture());
        Unit captValue = unitCaptor.getValue();
        assertNotNull(captValue.getDraft(), "Saved unit is composed with a nested draft unit");
        assertSame(captValue, captValue.getDraft().getDraftOf(), "Unit relation is set on draft Unit before save");
    }

    @Test
    public void update_draftWasRemoved_draftSetToNull() {
        // arrange - incoming dto without draft
        UnitDTO unitDTO = new UnitDTO();

        // arrange - existing Unit with draft
        Unit existingUnit = new Unit();
        Unit draft = spy(Unit.class);
        existingUnit.setDraft(draft);
        when(unitRepository.findById(any())).thenReturn(Optional.of(existingUnit));

        // stub repository response so model mapper doesn't throw exception after
        when(unitRepository.save(any())).thenReturn(mock(Unit.class));


        // act
        unitService.update(unitDTO);

        // assert
        verify(unitRepository).save(unitCaptor.capture());
        Unit captUnit = unitCaptor.getValue();
        assertNull(captUnit.getDraft(), "Draft should be set to null before saving");
        // verify draft association to it's owner was removed
        verify(draft).setDraftOf(null);
    }

    @Test
    public void update_draftWasAdded_draftAndAssociationsSet() {
        // arrange - incoming dto with draft
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setDraft(new UnitDTO());

        // arrange - existing Unit without draft
        Unit existingUnit = new Unit();
        when(unitRepository.findById(any())).thenReturn(Optional.of(existingUnit));

        // stub repository response so model mapper doesn't throw exception after
        when(unitRepository.save(any())).thenReturn(mock(Unit.class));

        // act
        unitService.update(unitDTO);

        // assert
        verify(unitRepository).save(unitCaptor.capture());
        Unit captUnit = unitCaptor.getValue();
        assertNotNull(captUnit.getDraft(), "Draft should be set before saving");
        assertSame(captUnit, captUnit.getDraft().getDraftOf(), "Unit relation is set on draft Unit before save");
    }
}