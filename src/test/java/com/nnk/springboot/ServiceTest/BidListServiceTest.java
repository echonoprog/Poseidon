package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
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
public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        BidList bid1 = new BidList();
        BidList bid2 = new BidList();
        List<BidList> bidLists = Arrays.asList(bid1, bid2);

        Mockito.when(bidListRepository.findAll()).thenReturn(bidLists);

        List<BidList> result = bidListService.findAll();

        Assert.assertEquals(2, result.size());
    }
    @Test
    public void testFindById() {
        BidList bid = new BidList();
        bid.setId(1);

        Mockito.when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        Optional<BidList> result = bidListService.findById(1);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(bid.getId(), result.get().getId());
    }

    @Test
    public void testSave() {
        BidList bid = new BidList();
        bid.setId(1);

        Mockito.when(bidListRepository.save(bid)).thenReturn(bid);

        BidList result = bidListService.save(bid);

        Assertions.assertEquals(bid.getId(), result.getId());
    }

    @Test
    public void testDeleteById() {
        Mockito.doNothing().when(bidListRepository).deleteById(1);

        bidListService.deleteById(1);

        Mockito.verify(bidListRepository, Mockito.times(1)).deleteById(1);
    }
}
