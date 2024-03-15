package com.nnk.springboot.ControllerTest;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
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

        mvc.perform(MockMvcRequestBuilders.get("/rating/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").roles("USER")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ratings"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAddRating() throws Exception {
        Rating ratingToAdd = new Rating();

        Mockito.when(ratingService.save(ArgumentMatchers.any(Rating.class))).thenReturn(ratingToAdd);

        mvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowAddRatingForm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("rating"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testUpdateRating() throws Exception {
        Integer id = 1;
        Rating updatedRating = new Rating();

        Mockito.when(ratingService.findById(id)).thenReturn(Optional.of(new Rating()));
        Mockito.when(ratingService.save(ArgumentMatchers.any(Rating.class))).thenReturn(updatedRating);

        mvc.perform(MockMvcRequestBuilders.post("/rating/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ratingAttribute\": \"value\"}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testDeleteRating() throws Exception {
        Integer id = 1;
        Rating rating = new Rating();

        Mockito.when(ratingService.findById(1)).thenReturn(Optional.of(rating));

        mvc.perform(MockMvcRequestBuilders.delete("/rating/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        Rating rating = new Rating();

        Mockito.when(ratingService.findById(id)).thenReturn(Optional.of(rating));

        mvc.perform(MockMvcRequestBuilders.get("/rating/update/{id}", id)
                        .with(SecurityMockMvcRequestPostProcessors.user("user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("rating/update"))
                .andExpect(MockMvcResultMatchers.model().attribute("rating", rating));
    }
}
