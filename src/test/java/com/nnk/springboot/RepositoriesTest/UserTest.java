package com.nnk.springboot.RepositoriesTest;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userTest() {

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");
        user.setFullname("Test User");
        user.setRole("ROLE_USER");


        user = userRepository.save(user);
        Assert.assertNotNull(user.getUsername());
        Assert.assertTrue("testuser".equals(user.getUsername()));



        user.setUsername("updateduser");
        user = userRepository.save(user);
        Assert.assertTrue(user.getUsername().equals("updateduser"));


        List<User> listResult = userRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);


        Integer id = user.getId();
        userRepository.delete(user);
        Optional<User> userList = userRepository.findById(id);
        Assert.assertFalse(userList.isPresent());
    }
}
