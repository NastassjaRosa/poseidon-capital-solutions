package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    @Test
    void shouldFindAllRuleNames() {
        RuleName rule1 = new RuleName("Rule1", "Desc1", "Json1", "Template1", "Sql1", "SqlPart1");
        RuleName rule2 = new RuleName("Rule2", "Desc2", "Json2", "Template2", "Sql2", "SqlPart2");

        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(rule1, rule2));

        List<RuleName> result = ruleNameService.findAll();

        assertEquals(2, result.size());
        verify(ruleNameRepository, times(1)).findAll();
    }

    @Test
    void shouldSaveRuleName() {
        RuleName rule = new RuleName("Rule1", "Desc1", "Json1", "Template1", "Sql1", "SqlPart1");

        when(ruleNameRepository.save(rule)).thenReturn(rule);

        RuleName result = ruleNameService.save(rule);

        assertNotNull(result);
        assertEquals("Rule1", result.getName());
        verify(ruleNameRepository, times(1)).save(rule);
    }

    @Test
    void shouldFindRuleNameById() {
        RuleName rule = new RuleName("Rule1", "Desc1", "Json1", "Template1", "Sql1", "SqlPart1");
        rule.setId(1);

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(rule));

        RuleName result = ruleNameService.findById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(ruleNameRepository, times(1)).findById(1);
    }

    @Test
    void shouldDeleteRuleNameById() {
        doNothing().when(ruleNameRepository).deleteById(1);

        ruleNameService.deleteById(1);

        verify(ruleNameRepository, times(1)).deleteById(1);
    }
}