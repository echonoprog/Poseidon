package com.nnk.springboot.RepositoriesTest;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurveTest {

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Test
    public void curvePointTest() {

        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(10);
        curvePoint.setAsOfDate(new Timestamp(System.currentTimeMillis()));
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);


        curvePoint = curvePointRepository.save(curvePoint);
        Assert.assertNotNull(curvePoint.getId());
        Assert.assertTrue(curvePoint.getCurveId() == 10);


        curvePoint.setCurveId(20);
        curvePoint = curvePointRepository.save(curvePoint);
        Assert.assertTrue(curvePoint.getCurveId() == 20);


        List<CurvePoint> listResult = curvePointRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);


        Integer id = curvePoint.getId();
        curvePointRepository.delete(curvePoint);
        Optional<CurvePoint> curvePointList = curvePointRepository.findById(id);
        Assert.assertFalse(curvePointList.isPresent());
    }
}
