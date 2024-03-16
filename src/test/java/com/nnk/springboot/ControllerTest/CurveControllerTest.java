package com.nnk.springboot.ControllerTest;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
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
public class CurveControllerTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
    private CurvePointService curvePointService;

    @InjectMocks
    private CurveController curveController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCurvePoints() throws Exception {
        List<CurvePoint> curvePoints = new ArrayList<>();
        Mockito.when(curvePointService.findAll()).thenReturn(curvePoints);

        mvc.perform(MockMvcRequestBuilders.get("/curvePoint/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").roles("USER")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoints"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAddCurvePoint() throws Exception {
        CurvePoint curvePointToAdd = new CurvePoint();

        Mockito.when(curvePointService.save(ArgumentMatchers.any(CurvePoint.class))).thenReturn(curvePointToAdd);

        mvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowAddCurveForm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/curvePoint/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testUpdateCurvePoint() throws Exception {
        Integer id = 1;
        CurvePoint updatedCurvePoint = new CurvePoint();

        Mockito.when(curvePointService.findById(id)).thenReturn(Optional.of(new CurvePoint()));
        Mockito.when(curvePointService.save(ArgumentMatchers.any(CurvePoint.class))).thenReturn(updatedCurvePoint);

        mvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"curvePointAttribute\": \"value\"}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testDeleteCurvePoint() throws Exception {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();

        Mockito.when(curvePointService.findById(1)).thenReturn(Optional.of(curvePoint));

        mvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();

        Mockito.when(curvePointService.findById(id)).thenReturn(Optional.of(curvePoint));

        mvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/{id}", id)
                        .with(SecurityMockMvcRequestPostProcessors.user("user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/update"))
                .andExpect(MockMvcResultMatchers.model().attribute("curvePoint", curvePoint));
    }
}
