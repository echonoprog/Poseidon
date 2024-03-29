package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
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
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testFindAll() {
        Trade trade1 = new Trade();
        Trade trade2 = new Trade();
        List<Trade> trades = Arrays.asList(trade1, trade2);

        Mockito.when(tradeRepository.findAll()).thenReturn(trades);

        List<Trade> result = tradeService.findAll();

        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testFindById() {
        Trade trade = new Trade();
        trade.setId(1);

        Mockito.when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Optional<Trade> result = tradeService.findById(1);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(trade.getId(), result.get().getId());
    }

    @Test
    public void testSave() {
        Trade trade = new Trade();
        trade.setId(1);

        Mockito.when(tradeRepository.save(trade)).thenReturn(trade);

        Trade result = tradeService.save(trade);

        Assert.assertEquals(trade.getId(), result.getId());
    }

    @Test
    public void testDeleteById() {
        Mockito.doNothing().when(tradeRepository).deleteById(1);

        tradeService.deleteById(1);

        Mockito.verify(tradeRepository, Mockito.times(1)).deleteById(1);
    }
}
