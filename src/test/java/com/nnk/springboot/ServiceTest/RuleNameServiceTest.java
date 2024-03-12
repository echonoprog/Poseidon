package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    @Test
    public void testFindAll() {
        RuleName ruleName1 = new RuleName();
        RuleName ruleName2 = new RuleName();
        List<RuleName> ruleNames = Arrays.asList(ruleName1, ruleName2);

        Mockito.when(ruleNameRepository.findAll()).thenReturn(ruleNames);

        List<RuleName> result = ruleNameService.findAll();

        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testFindById() {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);

        Mockito.when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        Optional<RuleName> result = ruleNameService.findById(1);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(ruleName.getId(), result.get().getId());
    }

    @Test
    public void testSave() {
        RuleName ruleName = new RuleName();
        ruleName.setId(1);

        Mockito.when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);

        RuleName result = ruleNameService.save(ruleName);

        Assert.assertEquals(ruleName.getId(), result.getId());
    }

    @Test
    public void testDeleteById() {
        Mockito.doNothing().when(ruleNameRepository).deleteById(1);

        ruleNameService.deleteById(1);

        Mockito.verify(ruleNameRepository, Mockito.times(1)).deleteById(1);
    }
}
