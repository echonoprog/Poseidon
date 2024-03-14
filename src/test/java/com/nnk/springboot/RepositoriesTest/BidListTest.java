package com.nnk.springboot.RepositoriesTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidListTest {

    @Autowired
    private BidListRepository bidListRepository;

    @Test
    public void bidListTest() {

        BidList bid = new BidList();
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);


        bid = bidListRepository.save(bid);
        Assert.assertNotNull(bid.getId());
        Assert.assertEquals(bid.getBidQuantity(), 10d, 0.001);


        bid.setBidQuantity(20d);
        bid.setAskQuantity(30d);
        bid.setBid(40d);
        bid.setAsk(50d);
        bid.setBenchmark("New Benchmark");
        bid.setBidListDate(new Timestamp(System.currentTimeMillis()));
        bid.setCommentary("New Commentary");
        bid.setSecurity("New Security");
        bid.setStatus("New Status");
        bid.setTrader("New Trader");
        bid.setBook("New Book");
        bid.setCreationName("New Creation Name");
        bid.setCreationDate(new Timestamp(System.currentTimeMillis()));
        bid.setRevisionName("New Revision Name");
        bid.setRevisionDate(new Timestamp(System.currentTimeMillis()));
        bid.setDealName("New Deal Name");
        bid.setDealType("New Deal Type");
        bid.setSourceListId("New Source List Id");
        bid.setSide("New Side");


        Assert.assertEquals(bid.getAccount(), "Account Test");
        Assert.assertEquals(bid.getType(), "Type Test");
        Assert.assertEquals(bid.getBidQuantity(), 20d, 0.001);
        Assert.assertEquals(bid.getAskQuantity(), 30d, 0.001);
        Assert.assertEquals(bid.getBid(), 40d, 0.001);
        Assert.assertEquals(bid.getAsk(), 50d, 0.001);
        Assert.assertEquals(bid.getBenchmark(), "New Benchmark");
        Assert.assertNotNull(bid.getBidListDate());
        Assert.assertEquals(bid.getCommentary(), "New Commentary");
        Assert.assertEquals(bid.getSecurity(), "New Security");
        Assert.assertEquals(bid.getStatus(), "New Status");
        Assert.assertEquals(bid.getTrader(), "New Trader");
        Assert.assertEquals(bid.getBook(), "New Book");
        Assert.assertEquals(bid.getCreationName(), "New Creation Name");
        Assert.assertNotNull(bid.getCreationDate());
        Assert.assertEquals(bid.getRevisionName(), "New Revision Name");
        Assert.assertNotNull(bid.getRevisionDate());
        Assert.assertEquals(bid.getDealName(), "New Deal Name");
        Assert.assertEquals(bid.getDealType(), "New Deal Type");
        Assert.assertEquals(bid.getSourceListId(), "New Source List Id");
        Assert.assertEquals(bid.getSide(), "New Side");


        List<BidList> listResult = bidListRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);


        Integer id = bid.getId();
        bidListRepository.delete(bid);


        Optional<BidList> bidList = bidListRepository.findById(id);
        Assert.assertFalse(bidList.isPresent());
    }

}
