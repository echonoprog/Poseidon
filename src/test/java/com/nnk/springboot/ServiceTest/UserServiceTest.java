package com.nnk.springboot.ServiceTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testFindAll() {
        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        Assert.assertEquals(2, result.size());
    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setId(1);

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1);

        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(user.getId(), result.get().getId());
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setId(1);

        Mockito.when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);

        Assert.assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testDeleteById() {
        Mockito.doNothing().when(userRepository).deleteById(1);

        userService.deleteById(1);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1);
    }
}
