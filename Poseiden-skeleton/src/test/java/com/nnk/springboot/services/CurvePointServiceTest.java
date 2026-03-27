package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    @Test
    void shouldFindAllCurvePoints() {
        CurvePoint curve1 = new CurvePoint(10, 10.0, 30.0);
        CurvePoint curve2 = new CurvePoint(20, 20.0, 40.0);

        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(curve1, curve2));

        List<CurvePoint> result = curvePointService.findAll();

        assertEquals(2, result.size());
        verify(curvePointRepository, times(1)).findAll();
    }

    @Test
    void shouldSaveCurvePoint() {
        CurvePoint curvePoint = new CurvePoint(10, 10.0, 30.0);

        when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);

        CurvePoint result = curvePointService.save(curvePoint);

        assertNotNull(result);
        assertEquals(10, result.getCurveId());
        verify(curvePointRepository, times(1)).save(curvePoint);
    }

    @Test
    void shouldFindCurvePointById() {
        CurvePoint curvePoint = new CurvePoint(10, 10.0, 30.0);
        curvePoint.setId(1);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));

        CurvePoint result = curvePointService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(curvePointRepository, times(1)).findById(1);
    }

    @Test
    void shouldDeleteCurvePointById() {
        doNothing().when(curvePointRepository).deleteById(1);

        curvePointService.deleteById(1);

        verify(curvePointRepository, times(1)).deleteById(1);
    }
}