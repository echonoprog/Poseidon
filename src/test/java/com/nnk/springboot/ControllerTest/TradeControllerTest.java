package com.nnk.springboot.ControllerTest;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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

@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
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

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("trades"));
    }

    @Test

    public void testAddTrade() throws Exception {
        Trade tradeToAdd = new Trade();

        Mockito.when(tradeService.save(ArgumentMatchers.any(Trade.class))).thenReturn(tradeToAdd);

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testUpdateTrade() throws Exception {
        Integer id = 1;
        Trade updatedTrade = new Trade();

        Mockito.when(tradeService.findById(id)).thenReturn(Optional.of(new Trade()));
        Mockito.when(tradeService.save(ArgumentMatchers.any(Trade.class))).thenReturn(updatedTrade);

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testDeleteTrade() throws Exception {
        Integer id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/trade/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        Trade trade = new Trade();

        Mockito.when(tradeService.findById(id)).thenReturn(Optional.of(trade));

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("trade/update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("trade"));
    }
}
