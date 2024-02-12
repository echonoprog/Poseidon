package com.nnk.springboot.etc.ControllerTest;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
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

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoints"));
    }

    @Test
    public void testAddCurvePoint() throws Exception {
        CurvePoint curvePointToAdd = new CurvePoint();

        Mockito.when(curvePointService.save(ArgumentMatchers.any(CurvePoint.class))).thenReturn(curvePointToAdd);

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testUpdateCurvePoint() throws Exception {
        Integer id = 1;
        CurvePoint updatedCurvePoint = new CurvePoint();

        Mockito.when(curvePointService.findById(id)).thenReturn(Optional.of(new CurvePoint()));
        Mockito.when(curvePointService.save(ArgumentMatchers.any(CurvePoint.class))).thenReturn(updatedCurvePoint);

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testDeleteCurvePoint() throws Exception {
        Integer id = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();

        Mockito.when(curvePointService.findById(id)).thenReturn(Optional.of(curvePoint));

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/update"))
                .andExpect(MockMvcResultMatchers.model().attribute("curvePoint", curvePoint));
    }
}
