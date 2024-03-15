package com.nnk.springboot.ControllerTest;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
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
public class UserControllerTest {

    @Autowired
    public MockMvc mvc;

    @MockBean
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

        mvc.perform(MockMvcRequestBuilders.get("/user/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").roles("USER")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAddUser() throws Exception {

        User userToAdd = new User();

        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(userToAdd);

        mvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "testUser")
                        .param("password", "password")
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
    }


    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowAddUserForm() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user/add")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testUpdateUser() throws Exception {
        Integer id = 1;
        User updatedUser = new User();

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(new User()));
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(updatedUser);

        mvc.perform(MockMvcRequestBuilders.post("/user/update/{id}", id)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "testUser")
                        .param("password", "password")
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
    }


    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testDeleteUser() throws Exception {
        Integer id = 1;
        User user = new User();

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));

        mvc.perform(MockMvcRequestBuilders.delete("/user/delete/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        User user = new User();

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        mvc.perform(MockMvcRequestBuilders.get("/user/update/{id}", id)
                        .with(SecurityMockMvcRequestPostProcessors.user("user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/update"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", user));
    }
}
