package com.nnk.springboot.RepositoriesTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingTest {

    @Autowired
    private RatingRepository ratingRepository;

    @Test
    public void ratingTest() {

        Rating rating = new Rating();
        rating.setMoodysRating("Moodys Rating");
        rating.setSandPRating("Sand PRating");
        rating.setFitchRating("Fitch Rating");
        rating.setOrderNumber(10);


        rating = ratingRepository.save(rating);


        Assert.assertNotNull(rating.getId());
        Assert.assertEquals("Moodys Rating", rating.getMoodysRating());
        Assert.assertEquals("Sand PRating", rating.getSandPRating());
        Assert.assertEquals("Fitch Rating", rating.getFitchRating());
        Assert.assertTrue(rating.getOrderNumber() == 10);

    }

}
