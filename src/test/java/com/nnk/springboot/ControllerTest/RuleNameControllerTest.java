package com.nnk.springboot.ControllerTest;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RuleNameService ruleNameService;

    @InjectMocks
    private RuleNameController ruleNameController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRuleNames() throws Exception {
        List<RuleName> ruleNames = new ArrayList<>();

        Mockito.when(ruleNameService.findAll()).thenReturn(ruleNames);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf())
                        .with(user("user").roles("USER")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ruleName/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ruleNames"));
    }

    @Test
    public void testAddRuleName() throws Exception {
        RuleName ruleNameToAdd = new RuleName();

        Mockito.when(ruleNameService.save(ArgumentMatchers.any(RuleName.class))).thenReturn(ruleNameToAdd);

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testUpdateRuleName() throws Exception {
        Integer id = 1;
        RuleName updatedRuleName = new RuleName();

        Mockito.when(ruleNameService.findById(id)).thenReturn(Optional.of(new RuleName()));
        Mockito.when(ruleNameService.save(ArgumentMatchers.any(RuleName.class))).thenReturn(updatedRuleName);

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testDeleteRuleName() throws Exception {
        Integer id = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Integer id = 3;
        RuleName ruleName = new RuleName();

        Mockito.when(ruleNameService.findById(id)).thenReturn(Optional.of(ruleName));

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/{id}", id)
                        .with(SecurityMockMvcRequestPostProcessors.user("ruleName"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ruleName/update"));

    }


}
