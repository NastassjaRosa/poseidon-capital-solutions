package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    public Trade findById(Integer id) {
        Optional<Trade> trade = tradeRepository.findById(id);
        return trade.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    }

    public void deleteById(Integer id) {
        tradeRepository.deleteById(id);
    }
}