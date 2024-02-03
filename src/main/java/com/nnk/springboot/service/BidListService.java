package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService {

    @Autowired

    private BidListRepository bidListRepository;

    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    public Optional<BidList> findById(Integer id) {
        return bidListRepository.findById(id);
    }

    public BidList save(BidList bidList) {
        return bidListRepository.save(bidList);
    }

    public void deleteById(Integer id) {
        bidListRepository.deleteById(id);
    }
}
