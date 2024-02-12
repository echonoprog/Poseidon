package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingTest {

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    public void ratingTest() {
        // Création d'un objet Rating
        Rating rating = new Rating();
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(10);

        // Enregistrement
        rating = ratingRepository.save(rating);
        Assert.assertNotNull(rating.getId());
        Assert.assertTrue(rating.getOrderNumber() == 10);

        // Mise à jour
        rating.setOrderNumber(20);
        rating = ratingRepository.save(rating);
        Assert.assertTrue(rating.getOrderNumber() == 20);

        // Recherche
        List<Rating> listResult = ratingRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Suppression
        Integer id = rating.getId();
        ratingRepository.delete(rating);
        Optional<Rating> ratingList = ratingRepository.findById(id);
        Assert.assertFalse(ratingList.isPresent());
    }
}
