package com.nnk.springboot.ControllerTest;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = new ArrayList<>();

        Mockito.when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
    }

    @Test
    public void testAddUser() throws Exception {
        User userToAdd = new User();

        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(userToAdd);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }


    @Test
    public void testValidateUser() throws Exception {
        User userToAdd = new User();

        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(userToAdd);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Integer id = 2;
        User user = new User();

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/{id}", id)
                        .with(SecurityMockMvcRequestPostProcessors.user("user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/update"));
    }


    @Test
    public void testUpdateUser() throws Exception {
        Integer id = 1;
        User updatedUser = new User();

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(new User()));
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    public void testDeleteUser() throws Exception {
        Integer id = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
}
