package com.nnk.springboot.ControllerTest;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
    private TradeService tradeService;

    @InjectMocks
    private TradeController tradeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllTrades() throws Exception {
        List<Trade> trades = new ArrayList<>();
        Mockito.when(tradeService.findAll()).thenReturn(trades);

        mvc.perform(MockMvcRequestBuilders.get("/trade/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").roles("USER")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("trades"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAddTrade() throws Exception {
        Trade tradeToAdd = new Trade();

        Mockito.when(tradeService.save(ArgumentMatchers.any(Trade.class))).thenReturn(tradeToAdd);

        mvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowAddTradeForm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/trade/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("trade"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testUpdateTrade() throws Exception {
        Integer id = 1;
        Trade updatedTrade = new Trade();

        Mockito.when(tradeService.findById(id)).thenReturn(Optional.of(new Trade()));
        Mockito.when(tradeService.save(ArgumentMatchers.any(Trade.class))).thenReturn(updatedTrade);

        mvc.perform(MockMvcRequestBuilders.post("/trade/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tradeAttribute\": \"value\"}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testDeleteTrade() throws Exception {
        Integer id = 1;
        Trade trade = new Trade();

        Mockito.when(tradeService.findById(1)).thenReturn(Optional.of(trade));

        mvc.perform(MockMvcRequestBuilders.get("/trade/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        Trade trade = new Trade();

        Mockito.when(tradeService.findById(id)).thenReturn(Optional.of(trade));

        mvc.perform(MockMvcRequestBuilders.get("/trade/update/{id}", id)
                        .with(SecurityMockMvcRequestPostProcessors.user("user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/update"))
                .andExpect(MockMvcResultMatchers.model().attribute("trade", trade));
    }
}
