package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @Test
    void shouldFindAllBidLists() {
        BidList bid1 = new BidList("Account1", "Type1", 10.0);
        BidList bid2 = new BidList("Account2", "Type2", 20.0);

        when(bidListRepository.findAll()).thenReturn(Arrays.asList(bid1, bid2));

        List<BidList> result = bidListService.findAll();

        assertEquals(2, result.size());
        verify(bidListRepository, times(1)).findAll();
    }

    @Test
    void shouldSaveBidList() {
        BidList bid = new BidList("Account1", "Type1", 10.0);

        when(bidListRepository.save(bid)).thenReturn(bid);

        BidList result = bidListService.save(bid);

        assertNotNull(result);
        assertEquals("Account1", result.getAccount());
        verify(bidListRepository, times(1)).save(bid);
    }

    @Test
    void shouldFindBidListById() {
        BidList bid = new BidList("Account1", "Type1", 10.0);
        bid.setBidListId(1);

        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        BidList result = bidListService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getBidListId());
        verify(bidListRepository, times(1)).findById(1);
    }

    @Test
    void shouldDeleteBidListById() {
        doNothing().when(bidListRepository).deleteById(1);

        bidListService.deleteById(1);

        verify(bidListRepository, times(1)).deleteById(1);
    }
}