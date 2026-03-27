package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @Test
    void shouldFindAllTrades() {
        Trade trade1 = new Trade("Account1", "Type1", 10.0);
        Trade trade2 = new Trade("Account2", "Type2", 20.0);

        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade1, trade2));

        List<Trade> result = tradeService.findAll();

        assertEquals(2, result.size());
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    void shouldSaveTrade() {
        Trade trade = new Trade("Account1", "Type1", 10.0);

        when(tradeRepository.save(trade)).thenReturn(trade);

        Trade result = tradeService.save(trade);

        assertNotNull(result);
        assertEquals("Account1", result.getAccount());
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void shouldFindTradeById() {
        Trade trade = new Trade("Account1", "Type1", 10.0);
        trade.setTradeId(1);

        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade result = tradeService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getTradeId());
        verify(tradeRepository, times(1)).findById(1);
    }

    @Test
    void shouldDeleteTradeById() {
        doNothing().when(tradeRepository).deleteById(1);

        tradeService.deleteById(1);

        verify(tradeRepository, times(1)).deleteById(1);
    }
}