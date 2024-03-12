package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @Test
    public void testFindAll() {
        BidList bid1 = new BidList();
        BidList bid2 = new BidList();
        List<BidList> bidList = Arrays.asList(bid1, bid2);

        Mockito.when(bidListRepository.findAll()).thenReturn(bidList);

        List<BidList> result = bidListService.findAll();

        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testFindById() {
        BidList bid = new BidList();
        bid.setId(1);

        Mockito.when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        Optional<BidList> result = bidListService.findById(1);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(bid.getId(), result.get().getId());
    }

    @Test
    public void testSave() {
        BidList bid = new BidList();
        bid.setId(1);

        Mockito.when(bidListRepository.save(bid)).thenReturn(bid);

        BidList result = bidListService.save(bid);

        Assert.assertEquals(bid.getId(), result.getId());
    }

    @Test
    public void testDeleteById() {
        Mockito.doNothing().when(bidListRepository).deleteById(1);

        bidListService.deleteById(1);

        Mockito.verify(bidListRepository, Mockito.times(1)).deleteById(1);
    }
}
