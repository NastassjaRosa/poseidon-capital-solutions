package com.nnk.springboot;

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
public class UserTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userTest() {
        User user = new User("userTest", "password", "User Test", "USER");

        user = userRepository.save(user);
        Assert.assertNotNull(user.getId());

        user.setUsername("userUpdate");
        user = userRepository.save(user);
        Assert.assertEquals("userUpdate", user.getUsername());

        List<User> list = userRepository.findAll();
        Assert.assertTrue(list.size() > 0);

        Integer id = user.getId();
        userRepository.delete(user);

        Optional<User> deleted = userRepository.findById(id);
        Assert.assertFalse(deleted.isPresent());
    }
}