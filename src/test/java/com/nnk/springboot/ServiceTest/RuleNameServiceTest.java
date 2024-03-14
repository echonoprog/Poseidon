package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

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
