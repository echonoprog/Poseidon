package com.nnk.springboot.RepositoriesTest;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeTest {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    public void tradeTest() {
        // Création d'un objet Trade
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");

        // Enregistrement
        trade = tradeRepository.save(trade);
        Assert.assertNotNull(trade.getId());
        Assert.assertTrue(trade.getAccount().equals("Trade Account"));

        // Mise à jour
        trade.setAccount("Trade Account Update");
        trade = tradeRepository.save(trade);
        Assert.assertTrue(trade.getAccount().equals("Trade Account Update"));

        // Recherche
        List<Trade> listResult = tradeRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Suppression
        Integer id = trade.getId();
        tradeRepository.delete(trade);
        Optional<Trade> tradeList = tradeRepository.findById(id);
        Assert.assertFalse(tradeList.isPresent());
    }
}
