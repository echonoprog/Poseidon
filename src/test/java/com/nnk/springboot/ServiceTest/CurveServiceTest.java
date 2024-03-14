package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;
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
public class CurveServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        CurvePoint curve1 = new CurvePoint();
        CurvePoint curve2 = new CurvePoint();
        List<CurvePoint> curvePoints = Arrays.asList(curve1, curve2);

        Mockito.when(curvePointRepository.findAll()).thenReturn(curvePoints);

        List<CurvePoint> result = curvePointService.findAll();

        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testFindById() {
        CurvePoint curve = new CurvePoint();
        curve.setId(1);

        Mockito.when(curvePointRepository.findById(1)).thenReturn(Optional.of(curve));

        Optional<CurvePoint> result = curvePointService.findById(1);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(curve.getId(), result.get().getId());
    }

    @Test
    public void testSave() {
        CurvePoint curve = new CurvePoint();
        curve.setId(1);

        Mockito.when(curvePointRepository.save(curve)).thenReturn(curve);

        CurvePoint result = curvePointService.save(curve);

        Assert.assertEquals(curve.getId(), result.getId());
    }

    @Test
    public void testDeleteById() {
        Mockito.doNothing().when(curvePointRepository).deleteById(1);

        curvePointService.deleteById(1);

        Mockito.verify(curvePointRepository, Mockito.times(1)).deleteById(1);
    }
}
