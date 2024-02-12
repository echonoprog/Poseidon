package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleNameTest {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Test
    public void ruleTest() {
        // Création d'un objet RuleName
        RuleName rule = new RuleName();
        rule.setName("Rule Name");
        rule.setDescription("Description");
        rule.setJson("Json");
        rule.setTemplate("Template");
        rule.setSqlStr("SQL");
        rule.setSqlPart("SQL Part");

        // Enregistrement
        rule = ruleNameRepository.save(rule);
        Assert.assertNotNull(rule.getId());
        Assert.assertTrue(rule.getName().equals("Rule Name"));

        // Mise à jour
        rule.setName("Rule Name Update");
        rule = ruleNameRepository.save(rule);
        Assert.assertTrue(rule.getName().equals("Rule Name Update"));

        // Recherche
        List<RuleName> listResult = ruleNameRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Suppression
        Integer id = rule.getId();
        ruleNameRepository.delete(rule);
        Optional<RuleName> ruleList = ruleNameRepository.findById(id);
        Assert.assertFalse(ruleList.isPresent());
    }
}
