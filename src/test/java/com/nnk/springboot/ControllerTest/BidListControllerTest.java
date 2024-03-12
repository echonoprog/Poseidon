package com.nnk.springboot.ControllerTest;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
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
public class BidListControllerTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
    private BidListService bidListService;

    @InjectMocks
    private BidListController bidListController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBidLists() throws Exception {
        List<BidList> bidLists = new ArrayList<>();
        Mockito.when(bidListService.findAll()).thenReturn(bidLists);

        mvc.perform(MockMvcRequestBuilders.get("/bidList/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").roles("USER")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bidList/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("bidLists"));
    }



    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAddBidList() throws Exception {
        BidList bidListToAdd = new BidList();


        Mockito.when(bidListService.save(ArgumentMatchers.any(BidList.class))).thenReturn(bidListToAdd);

        mvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"));
    }




    @Test
    public void testUpdateBidList() throws Exception {
        Integer id = 1;
        BidList updatedBidList = new BidList();

        Mockito.when(bidListService.findById(id)).thenReturn(Optional.of(new BidList()));
        Mockito.when(bidListService.save(ArgumentMatchers.any(BidList.class))).thenReturn(updatedBidList);

        mvc.perform(MockMvcRequestBuilders.post("/bidList/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }


    @Test
    public void testDeleteBidList() throws Exception {
        Integer id = 1;

        mvc.perform(MockMvcRequestBuilders.delete("/bidList/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        BidList bidList = new BidList();

        Mockito.when(bidListService.findById(id)).thenReturn(Optional.of(bidList));

        mvc.perform(MockMvcRequestBuilders.get("/bidList/update/{id}", id)
                        .with(SecurityMockMvcRequestPostProcessors.user("user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bidList/update"))
                .andExpect(MockMvcResultMatchers.model().attribute("bidList", bidList));
    }
}