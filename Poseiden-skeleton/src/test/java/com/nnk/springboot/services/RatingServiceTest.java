package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    void shouldFindAllRatings() {
        Rating rating1 = new Rating("Moodys1", "SandP1", "Fitch1", 10);
        Rating rating2 = new Rating("Moodys2", "SandP2", "Fitch2", 20);

        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        List<Rating> result = ratingService.findAll();

        assertEquals(2, result.size());
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void shouldSaveRating() {
        Rating rating = new Rating("Moodys1", "SandP1", "Fitch1", 10);

        when(ratingRepository.save(rating)).thenReturn(rating);

        Rating result = ratingService.save(rating);

        assertNotNull(result);
        assertEquals("Moodys1", result.getMoodysRating());
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void shouldFindRatingById() {
        Rating rating = new Rating("Moodys1", "SandP1", "Fitch1", 10);
        rating.setId(1);

        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Rating result = ratingService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(ratingRepository, times(1)).findById(1);
    }

    @Test
    void shouldThrowExceptionWhenRatingNotFound() {
        when(ratingRepository.findById(99)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> ratingService.findById(99)
        );

        assertEquals("Invalid rating Id:99", exception.getMessage());
        verify(ratingRepository, times(1)).findById(99);
    }

    @Test
    void shouldDeleteRatingById() {
        doNothing().when(ratingRepository).deleteById(1);

        ratingService.deleteById(1);

        verify(ratingRepository, times(1)).deleteById(1);
    }
}