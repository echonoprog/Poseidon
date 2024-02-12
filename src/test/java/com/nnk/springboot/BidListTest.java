package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListTest {

    @Autowired
    private BidListRepository bidListRepository;

    @Test
    public void bidListTest() {
        // Création d'un objet BidList
        BidList bid = new BidList();
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        // Enregistrement
        bid = bidListRepository.save(bid);
        Assert.assertNotNull(bid.getId());
        Assert.assertEquals(bid.getBidQuantity(), 10d, 0.001);

        // Mise à jour
        bid.setBidQuantity(20d);
        bid = bidListRepository.save(bid);
        Assert.assertEquals(bid.getBidQuantity(), 20d, 0.001);

        // Recherche
        List<BidList> listResult = bidListRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Suppression
        Integer id = bid.getId();
        bidListRepository.delete(bid);
        Optional<BidList> bidList = bidListRepository.findById(id);
        Assert.assertFalse(bidList.isPresent());
    }
}
