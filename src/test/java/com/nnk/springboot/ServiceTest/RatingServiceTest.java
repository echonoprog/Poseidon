package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;


    @InjectMocks
    private RatingService ratingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        Rating rating1 = new Rating();
        Rating rating2 = new Rating();
        List<Rating> ratings = Arrays.asList(rating1, rating2);

        Mockito.when(ratingRepository.findAll()).thenReturn(ratings);

        List<Rating> result = ratingService.findAll();

        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testFindById() {
        Rating rating = new Rating();
        rating.setId(1);

        Mockito.when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Optional<Rating> result = ratingService.findById(1);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(rating.getId(), result.get().getId());
    }

    @Test
    public void testSave() {
        Rating rating = new Rating();
        rating.setId(1);

        Mockito.when(ratingRepository.save(rating)).thenReturn(rating);

        Rating result = ratingService.save(rating);

        Assert.assertEquals(rating.getId(), result.getId());
    }

    @Test
    public void testDeleteById() {
        Mockito.doNothing().when(ratingRepository).deleteById(1);

        ratingService.deleteById(1);

        Mockito.verify(ratingRepository, Mockito.times(1)).deleteById(1);
    }
}
