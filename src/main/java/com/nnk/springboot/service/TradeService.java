package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> findById(Integer tradeId) {
        return tradeRepository.findById(tradeId);
    }

    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    public void deleteById(Integer tradeId) {
        tradeRepository.deleteById(tradeId);
    }
}
