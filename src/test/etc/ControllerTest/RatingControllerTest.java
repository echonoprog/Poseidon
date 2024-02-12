package com.nnk.springboot.etc.ControllerTest;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRatings() throws Exception {
        List<Rating> ratings = new ArrayList<>();

        Mockito.when(ratingService.findAll()).thenReturn(ratings);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ratings"));
    }

    @Test
    public void testAddRating() throws Exception {
        Rating ratingToAdd = new Rating();

        Mockito.when(ratingService.save(ArgumentMatchers.any(Rating.class))).thenReturn(ratingToAdd);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testUpdateRating() throws Exception {
        Integer id = 1;
        Rating updatedRating = new Rating();

        Mockito.when(ratingService.findById(id)).thenReturn(Optional.of(new Rating()));
        Mockito.when(ratingService.save(ArgumentMatchers.any(Rating.class))).thenReturn(updatedRating);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testDeleteRating() throws Exception {
        Integer id = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        Rating rating = new Rating();

        Mockito.when(ratingService.findById(id)).thenReturn(Optional.of(rating));

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/update"))
                .andExpect(MockMvcResultMatchers.model().attribute("rating", rating));
    }
}
